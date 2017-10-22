package jrat.module.registry;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.AbstractOutgoingPacket;

import java.io.DataOutputStream;


public class Packet79BrowseRegistry extends AbstractOutgoingPacket {

	private String location;

	public Packet79BrowseRegistry(String location) {
		this.location = location;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(location);
	}

	@Override
	public short getPacketId() {
		return 79;
	}

}
