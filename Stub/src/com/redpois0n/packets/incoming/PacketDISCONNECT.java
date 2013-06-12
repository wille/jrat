package com.redpois0n.packets.incoming;

public class PacketDISCONNECT extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		System.exit(0);
	}

}
