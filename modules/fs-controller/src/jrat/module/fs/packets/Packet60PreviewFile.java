package jrat.module.fs.packets;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;


public class Packet60PreviewFile implements OutgoingPacket {

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
