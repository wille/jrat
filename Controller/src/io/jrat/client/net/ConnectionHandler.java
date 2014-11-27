package io.jrat.client.net;

import io.jrat.client.AbstractSlave;
import io.jrat.client.Main;
import io.jrat.client.SampleMode;
import io.jrat.client.Slave;
import io.jrat.client.Sound;
import io.jrat.client.addons.PluginEventHandler;
import io.jrat.client.exceptions.CloseException;
import io.jrat.client.packets.outgoing.Packet40Thumbnail;
import io.jrat.client.ui.frames.Frame;
import io.jrat.client.utils.TrayIconUtils;
import io.jrat.client.utils.Utils;

public class ConnectionHandler {

	public synchronized static void addSlave(AbstractSlave slave) {
		Main.connections.add(slave);

		Object icon = null;

		if (Frame.thumbnails && slave instanceof Slave) {
			icon = "...";
			((Slave)slave).addToSendQueue(new Packet40Thumbnail());
		} else {
			icon = slave.getCountry();
		}

		Frame.mainModel.addRow(new Object[] { icon, "Unknown", "Loading...", slave.getIP(), "?", "Unknown", "Unknown", "0 mb", "?", slave.getVersion() });

		String title = Main.formatTitle();
		Main.instance.setTitle(title);

		TrayIconUtils.setTitle(title);
		TrayIconUtils.showMessage(title, slave.getIP() + " connected");

		Sound.playAdd();
		PluginEventHandler.onConnect(slave);
	}

	public synchronized static void removeSlave(AbstractSlave client, Exception e) {
		if (SampleMode.isInSampleMode()) {
			return;
		}

		Main.connections.remove(client);
		try {
			Frame.mainModel.removeRow(Utils.getRow(3, client.getIP()));
		} catch (Exception ex) {
		}
		String title = Main.formatTitle();
		Main.instance.setTitle(title);
		TrayIconUtils.setTitle(title);
		client.closeSocket(new CloseException("Removing connection..."));
		Sound.playRemove();
	}

}
