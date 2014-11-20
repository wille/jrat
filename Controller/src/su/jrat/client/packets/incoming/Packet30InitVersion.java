package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import su.jrat.client.Slave;


public class Packet30InitVersion extends AbstractIncomingPacket {

	public void read(Slave slave, DataInputStream dis) throws Exception {
		String version = slave.readLine();
		slave.setVersion(version);
	}

}
