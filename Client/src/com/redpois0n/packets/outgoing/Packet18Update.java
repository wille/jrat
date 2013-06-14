package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

public class Packet18Update extends AbstractOutgoingPacket {

	private String url;
	
	public Packet18Update(String url) {
		this.url = url;
	}
	
	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(url);
	}

	@Override
	public byte getPacketId() {
		return 18;
	}

}
