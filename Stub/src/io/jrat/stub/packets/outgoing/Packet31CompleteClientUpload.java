package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;

public class Packet31CompleteClientUpload extends AbstractOutgoingPacket {
	
	private String file;
	
	public Packet31CompleteClientUpload(String file) {
		this.file = file;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(file);
	}

	@Override
	public byte getPacketId() {
		return (byte) 31;
	}

}
