package com.redpois0n.net;

import com.redpois0n.Main;
import com.redpois0n.SampleMode;
import com.redpois0n.Slave;
import com.redpois0n.Sound;
import com.redpois0n.exceptions.CloseException;
import com.redpois0n.packets.Header;
import com.redpois0n.plugins.PluginEventHandler;
import com.redpois0n.ui.frames.Frame;
import com.redpois0n.utils.TrayIconUtils;
import com.redpois0n.utils.Util;

public class ConnectionHandler {

	public synchronized static void addSlave(Slave slave) {
		Main.connections.add(slave);
		
		Object icon = null;
		
		if (Frame.thumbnails) {
			icon = "...";
			slave.addToSendQueue(Header.THUMBNAIL);
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

	public static void removeSlave(Slave client, Exception e) {
		if (SampleMode.isInSampleMode()) {
			return;
		}
		
		Main.connections.remove(client);
		try {
			Frame.mainModel.removeRow(Util.getRow(3, client.getIP()));
		} catch (Exception ex) {
		}
		String title = Main.formatTitle();
		Main.instance.setTitle(title);
		TrayIconUtils.setTitle(title);
		client.closeSocket(new CloseException("Removing connection..."));
		Sound.playRemove();
	}

}
