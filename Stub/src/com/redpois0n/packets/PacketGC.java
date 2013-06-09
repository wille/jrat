package com.redpois0n.packets;

public class PacketGC extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		System.gc();
	}

}
