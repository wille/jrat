package com.redpois0n.packets.outgoing;


public class Packet18OneRemoteScreen extends Packet17RemoteScreen {

	public Packet18OneRemoteScreen(int width, int height, int x, int y) {
		super(width, height, x, y);
	}
	
	@Override
	public byte getPacketId() {
		return 18;
	}

}
