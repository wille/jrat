package jrat.module.fs.packets;

import jrat.client.Connection;
import jrat.client.packets.outgoing.AbstractOutgoingPacket;


public class Packet34CustomDirectory extends AbstractOutgoingPacket {

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
