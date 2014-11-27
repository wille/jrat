package io.jrat.client.packets.incoming;

import io.jrat.client.Antivirus;
import io.jrat.client.Slave;

import java.io.DataInputStream;


public class Packet69InitAntivirus extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int len = dis.readInt();

		Antivirus[] antiviruses = new Antivirus[len];

		for (int i = 0; i < len; i++) {
			antiviruses[i] = new Antivirus(slave.readLine());
		}

		slave.setAntiviruses(antiviruses);
	}

}
