package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Uninstaller;

public class Packet36Uninstall extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		new Uninstaller().start();
	}

}
