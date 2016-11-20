package io.jrat.controller.net;

import io.jrat.controller.AbstractSlave;
import io.jrat.controller.Main;
import io.jrat.controller.SampleMode;
import io.jrat.controller.addons.PluginEventHandler;
import io.jrat.controller.exceptions.CloseException;
import io.jrat.controller.exceptions.DuplicateSlaveException;
import io.jrat.controller.settings.StoreOfflineSlaves;
import io.jrat.controller.utils.TrayIconUtils;
import java.util.Timer;
import java.util.TimerTask;

public class ConnectionHandler {

	public synchronized static void addSlave(AbstractSlave slave) {
		if (slave.isAdded()) {
			slave.disconnect(new DuplicateSlaveException());
		} else {
			slave.setAdded(true);
			Main.connections.add(slave);

			Main.instance.getPanelClients().addSlave(slave);

			String title = Main.formatTitle();
			Main.instance.setTitle(title);

			TrayIconUtils.setToolTip(title);
			TrayIconUtils.showMessage(title, slave.getIP() + " connected");

			PluginEventHandler.onConnect(slave);
		}	
	}

	public synchronized static void removeSlave(final AbstractSlave client, int delay) {
		if (SampleMode.isInSampleMode()) {
			return;
		}

		Main.connections.remove(client);

		String title = Main.formatTitle();
		Main.instance.setTitle(title);
		TrayIconUtils.setToolTip(title);
		client.closeSocket(new CloseException("Removing connection..."));

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				for (AbstractSlave as : StoreOfflineSlaves.getGlobal().getList()) {
					if (as.equals(client)) {
						Main.instance.getPanelClients().addSlave(as);
					}
				}

				Main.instance.getPanelClients().removeSlave(client);
			}
		}, delay);
	}

}
