package su.jrat.client.addons;

import jrat.api.Packet;
import jrat.api.events.OnConnectEvent;
import jrat.api.events.OnDisconnectEvent;
import jrat.api.events.OnPacketEvent;
import jrat.api.events.OnSendPacketEvent;
import su.jrat.client.AbstractSlave;

public class PluginEventHandler {

	public static void onPacket(AbstractSlave slave, byte header) {
		for (Plugin plugin : PluginLoader.plugins) {
			try {
				plugin.getMethods().get(Plugin.ON_PACKET).invoke(plugin.getInstance(), new Object[] { new OnPacketEvent(RATObjectFormat.format(slave), new Packet(header)) });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void onConnect(AbstractSlave slave) {
		for (Plugin plugin : PluginLoader.plugins) {
			try {
				plugin.getMethods().get(Plugin.ON_CONNECT).invoke(plugin.getInstance(), new Object[] { new OnConnectEvent(RATObjectFormat.format(slave)) });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void onDisconnect(AbstractSlave slave) {
		for (Plugin plugin : PluginLoader.plugins) {
			try {
				plugin.getMethods().get(Plugin.ON_DISCONNECT).invoke(plugin.getInstance(), new Object[] { new OnDisconnectEvent(RATObjectFormat.format(slave)) });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void onSendPacket(byte header, AbstractSlave slave) {
		for (Plugin plugin : PluginLoader.plugins) {
			try {
				plugin.getMethods().get(Plugin.ON_SEND_PACKET).invoke(plugin.getInstance(), new Object[] { new OnSendPacketEvent(new Packet(header), RATObjectFormat.format(slave)) });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
