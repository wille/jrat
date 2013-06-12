package com.redpois0n.packets.in;

public class PacketDISCONNECT extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		System.exit(0);
	}

}
