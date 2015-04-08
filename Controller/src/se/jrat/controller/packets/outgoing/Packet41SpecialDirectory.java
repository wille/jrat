package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.controller.Slave;


public class Packet41SpecialDirectory extends AbstractOutgoingPacket {

	private String directory;

	public Packet41SpecialDirectory(String directory) {
		this.directory = directory;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(directory);
	}

	@Override
	public byte getPacketId() {
		return 41;
	}

}