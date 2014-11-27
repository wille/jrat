package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


public class Packet26InitJavaPath extends AbstractOutgoingPacket {

	private String path;

	public Packet26InitJavaPath(String path) {
		this.path = path;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(path);
	}

	@Override
	public byte getPacketId() {
		return (byte) 26;
	}

}
