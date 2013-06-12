package com.redpois0n.packets.in;

public class PacketGC extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		System.gc();
	}

}
