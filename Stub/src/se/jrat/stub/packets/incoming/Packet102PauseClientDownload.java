package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.common.io.FileCache;
import se.jrat.stub.Connection;

public class Packet102PauseClientDownload extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		File file = new File(Connection.instance.readLine());
		
		FileCache.get(file).getRunnable().pause();
	}

}
