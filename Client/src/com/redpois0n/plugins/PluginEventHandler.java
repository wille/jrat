package com.redpois0n.plugins;

import org.jrat.project.api.Packet;
import org.jrat.project.api.events.OnConnectEvent;
import org.jrat.project.api.events.OnDisconnectEvent;
import org.jrat.project.api.events.OnPacketEvent;
import org.jrat.project.api.events.OnSendPacketEvent;

import com.redpois0n.Slave;

public class PluginEventHandler {

	public static void onPacket(Slave slave, byte header) {
		for (Plugin plugin : PluginLoader.plugins) {
			try {
				plugin.getMethods().get("onpacket").invoke(plugin.getInstance(), new Object[] { new OnPacketEvent(RATObjectFormat.format(slave), new Packet(header)) });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void onConnect(Slave slave) {
		for (Plugin plugin : PluginLoader.plugins) {
			try {
				plugin.getMethods().get("onconnect").invoke(plugin.getInstance(), new Object[] { new OnConnectEvent(RATObjectFormat.format(slave)) });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void onDisconnect(Slave slave) {
		for (Plugin plugin : PluginLoader.plugins) {
			try {
				plugin.getMethods().get("ondisconnect").invoke(plugin.getInstance(), new Object[] { new OnDisconnectEvent(RATObjectFormat.format(slave)) });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void onSendPacket(byte header, Slave slave) {
		for (Plugin plugin : PluginLoader.plugins) {
			try {
				plugin.getMethods().get("onsendpacket").invoke(plugin.getInstance(), new Object[] { new OnSendPacketEvent(new Packet(header), RATObjectFormat.format(slave)) });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
