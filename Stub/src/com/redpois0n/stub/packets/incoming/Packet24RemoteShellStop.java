package com.redpois0n.stub.packets.incoming;

import com.redpois0n.stub.RemoteShell;

public class Packet24RemoteShellStop extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (RemoteShell.p != null) {
			RemoteShell.p.destroy();
		}
	}

}
