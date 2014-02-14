package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;

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
	public byte getPacketId() {
		return 14;
	}

}
