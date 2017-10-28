package jrat.client.packets.outgoing;

import jrat.client.Connection;


public interface OutgoingPacket {

	void write(Connection dos) throws Exception;

	short getPacketId();
}
