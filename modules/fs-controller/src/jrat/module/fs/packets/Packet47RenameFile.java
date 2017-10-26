package jrat.module.fs.packets;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.AbstractOutgoingPacket;


public class Packet47RenameFile extends AbstractOutgoingPacket {

	private String file;
	private String dest;

	public Packet47RenameFile(String file, String dest) {
		this.file = file;
		this.dest = dest;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(file);
		slave.writeLine(dest);
	}

	@Override
	public short getPacketId() {
		return 47;
	}

}
