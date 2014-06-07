package su.jrat.stub.packets.incoming;

import su.jrat.stub.Connection;

public class Packet45Reconnect extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.socket.close();
	}

}
