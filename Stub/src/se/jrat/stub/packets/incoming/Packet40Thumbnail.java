package se.jrat.stub.packets.incoming;

import se.jrat.stub.Screen;

public class Packet40Thumbnail extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Screen.sendThumbnail();
	}

}
