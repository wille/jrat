package se.jrat.client.net;

import se.jrat.client.AbstractSlave;
import se.jrat.client.Main;
import se.jrat.client.SampleMode;
import se.jrat.client.Slave;
import se.jrat.client.addons.PluginEventHandler;
import se.jrat.client.exceptions.CloseException;
import se.jrat.client.packets.outgoing.Packet40Thumbnail;
import se.jrat.client.ui.frames.Frame;
import se.jrat.client.utils.TrayIconUtils;

public class ConnectionHandler {

	public synchronized static void addSlave(AbstractSlave slave) {
		Main.connections.add(slave);

		if (Frame.thumbnails && slave instanceof Slave) {
			((Slave)slave).addToSendQueue(new Packet40Thumbnail());
		}

		Main.instance.getPanelClients().addSlave(slave);

		String title = Main.formatTitle();
		Main.instance.setTitle(title);

		TrayIconUtils.setTitle(title);
		TrayIconUtils.showMessage(title, slave.getIP() + " connected");

		PluginEventHandler.onConnect(slave);
	}

	public synchronized static void removeSlave(AbstractSlave client, Exception e) {
		if (SampleMode.isInSampleMode()) {
			return;
		}

		Main.connections.remove(client);

		String title = Main.formatTitle();
		Main.instance.setTitle(title);
		TrayIconUtils.setTitle(title);
		client.closeSocket(new CloseException("Removing connection..."));
		
		Main.instance.getPanelClients().removeSlave(client);
	}

}
