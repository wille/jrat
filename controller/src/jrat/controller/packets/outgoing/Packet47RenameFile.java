package jrat.controller.packets.outgoing;

import jrat.controller.Slave;

import java.io.DataOutputStream;


public class Packet47RenameFile extends AbstractOutgoingPacket {

	private String file;
	private String dest;

	public Packet47RenameFile(String file, String dest) {
		this.file = file;
		this.dest = dest;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(file);
		slave.writeLine(dest);
	}

	@Override
	public short getPacketId() {
		return 47;
	}

}
