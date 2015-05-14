package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.stub.Connection;

public class Packet87DeleteErrorLog extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		File errlog = new File("err.dat");
		if (errlog.exists()) {
			errlog.delete();
		}
	}

}
