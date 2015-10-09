package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;

import java.io.File;

public class Packet87DeleteErrorLog extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		File errlog = new File("err.dat");
		if (errlog.exists()) {
			errlog.delete();
		}
	}

}
