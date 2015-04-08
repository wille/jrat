package se.jrat.stub.packets.incoming;

import se.jrat.stub.Uninstaller;

public class Packet36Uninstall extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new Uninstaller().start();
	}

}
