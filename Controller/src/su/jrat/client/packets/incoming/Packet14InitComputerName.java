package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import su.jrat.client.Slave;


public class Packet14InitComputerName extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.setComputerName(slave.readLine());
	}
}
