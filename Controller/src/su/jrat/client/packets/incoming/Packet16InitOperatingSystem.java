package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import su.jrat.client.Slave;


public class Packet16InitOperatingSystem extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.setOperatingSystem(slave.readLine());
	}

}
