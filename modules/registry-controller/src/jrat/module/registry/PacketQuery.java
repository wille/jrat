package jrat.module.registry;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.AbstractOutgoingPacket;


public class PacketQuery extends AbstractOutgoingPacket {

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
