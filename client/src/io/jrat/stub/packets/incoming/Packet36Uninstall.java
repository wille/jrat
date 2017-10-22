package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.Uninstaller;

public class Packet36Uninstall extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		new Uninstaller().start();
	}

}
