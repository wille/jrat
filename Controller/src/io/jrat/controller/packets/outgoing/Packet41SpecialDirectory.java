package io.jrat.controller.packets.outgoing;

import io.jrat.controller.Slave;
import java.io.DataOutputStream;


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
	public short getPacketId() {
		return 41;
	}

}
