package su.jrat.stub.packets.incoming;

import su.jrat.stub.Connection;
import su.jrat.stub.Main;

public class Packet99Encryption extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Main.encryption = Connection.readBoolean();
	}

}
