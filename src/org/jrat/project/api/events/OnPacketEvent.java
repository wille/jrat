package org.jrat.project.api.events;

import org.jrat.project.api.Packet;
import org.jrat.project.api.RATObject;

public class OnPacketEvent extends RATObjectEvent {

	private final Packet packet;

	public OnPacketEvent(RATObject server, Packet packet) {
		super(server);
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}

}
