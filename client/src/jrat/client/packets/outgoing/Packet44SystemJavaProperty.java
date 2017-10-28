package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet44SystemJavaProperty implements OutgoingPacket {

	private String key;
	private String value;

	public Packet44SystemJavaProperty(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public void write(Connection con) throws Exception {
        con.writeLine(key);
        con.writeLine(value);
	}

	@Override
	public short getPacketId() {
		return 44;
	}

}
