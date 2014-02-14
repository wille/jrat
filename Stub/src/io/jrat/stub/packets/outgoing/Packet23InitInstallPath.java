package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


public class Packet23InitInstallPath extends AbstractOutgoingPacket {

	private String path;

	public Packet23InitInstallPath(String path) {
		this.path = path;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(path);
	}

	@Override
	public byte getPacketId() {
		return (byte) 23;
	}

}
