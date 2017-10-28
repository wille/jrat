package jrat.module.fs.packets;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;
import jrat.module.fs.FileSearch;

public class Packet54StopSearch implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		FileSearch.stopSearch();
	}

}
