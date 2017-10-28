package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.Uninstaller;

public class Packet36Uninstall implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		new Uninstaller().start();
	}

}
