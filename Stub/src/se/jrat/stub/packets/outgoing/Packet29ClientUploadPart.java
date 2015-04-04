package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet29ClientUploadPart extends AbstractOutgoingPacket {

	private String file;
	private byte[] part;
	private int to;
	
	public Packet29ClientUploadPart(String file, byte[] part, int to) {
		this.file = file;
		this.part = part;
		this.to = to;
	}
	
	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(file);

		dos.writeInt(to);
		dos.write(part, 0, to);
	}

	@Override
	public byte getPacketId() {
		return (byte) 29;
	}

}