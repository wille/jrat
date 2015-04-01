package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;

import se.jrat.common.io.StringWriter;

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
