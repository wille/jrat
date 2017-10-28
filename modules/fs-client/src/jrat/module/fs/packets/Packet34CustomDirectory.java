package jrat.module.fs.packets;

import jrat.client.Connection;
import jrat.client.packets.outgoing.OutgoingPacket;


public class Packet34CustomDirectory implements OutgoingPacket {

	private String location;

	public Packet34CustomDirectory(String location) {
		this.location = location;
	}

	@Override
	public void write(Connection con) throws Exception {
        con.writeLine(this.location);
	}

	@Override
	public short getPacketId() {
		return 34;
	}

}
