package com.redpois0n.packets;

public class PacketGC extends Packet {

	@Override
	public void read(String line) throws Exception {
		System.gc();
	}

}
