package su.jrat.stub.packets.incoming;

import su.jrat.stub.Elevation;

public class Packet100RequestElevation extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Elevation.executeElevation();
	}

}
