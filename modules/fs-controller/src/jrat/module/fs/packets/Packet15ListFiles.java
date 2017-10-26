package jrat.module.fs.packets;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.AbstractOutgoingPacket;


public class Packet15ListFiles extends AbstractOutgoingPacket {

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
