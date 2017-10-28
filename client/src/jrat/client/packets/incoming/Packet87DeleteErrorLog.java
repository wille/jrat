package jrat.client.packets.incoming;

import jrat.client.Connection;

import java.io.File;

public class Packet87DeleteErrorLog implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		File errlog = new File("err.dat");
		if (errlog.exists()) {
			errlog.delete();
		}
	}

}
