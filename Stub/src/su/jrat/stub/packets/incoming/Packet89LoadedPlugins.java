package su.jrat.stub.packets.incoming;

import su.jrat.stub.Connection;
import su.jrat.stub.packets.outgoing.Packet67LoadedPlugins;

public class Packet89LoadedPlugins extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.addToSendQueue(new Packet67LoadedPlugins());
	}

}
