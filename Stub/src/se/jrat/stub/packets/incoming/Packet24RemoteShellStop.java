package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.RemoteShell;

public class Packet24RemoteShellStop extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		if (RemoteShell.getInstance() != null) {
			RemoteShell.getInstance().destroy();
		}
	}

}
