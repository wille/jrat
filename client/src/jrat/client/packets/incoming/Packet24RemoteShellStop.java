package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.RemoteShell;

public class Packet24RemoteShellStop implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		if (RemoteShell.getInstance() != null) {
			RemoteShell.getInstance().destroy();
		}
	}

}
