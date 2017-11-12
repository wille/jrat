package jrat.module.fs.packets.download;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;
import jrat.common.io.FileCache;
import jrat.common.io.TransferData;

public class Packet104DownloadPart implements IncomingPacket {

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
