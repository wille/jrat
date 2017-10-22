package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.RemoteShell;

public class Packet24RemoteShellStop extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		if (RemoteShell.getInstance() != null) {
			RemoteShell.getInstance().destroy();
		}
	}

}
