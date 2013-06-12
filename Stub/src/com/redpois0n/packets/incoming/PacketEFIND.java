package com.redpois0n.packets.incoming;

import com.redpois0n.Search;

public class PacketEFIND extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Search.running = false;
	}

}
