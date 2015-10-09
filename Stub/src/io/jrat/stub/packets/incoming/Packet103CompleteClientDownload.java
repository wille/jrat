package io.jrat.stub.packets.incoming;

import io.jrat.common.io.FileCache;
import io.jrat.stub.Connection;


public class Packet103CompleteClientDownload extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = con.readLine();
		
		FileCache.remove(file);
	}

}
