package jrat.module.system.packets;

import jrat.client.Connection;
import jrat.client.packets.outgoing.OutgoingPacket;


public class Packet53Startup implements OutgoingPacket {

	private String key;

	public Packet53Startup(String key) {
		this.key = key;
	}

	@Override
	public void write(Connection con) throws Exception {
        con.writeLine(key);
	}

	@Override
	public short getPacketId() {
		return 53;
	}

}
