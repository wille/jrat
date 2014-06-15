package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import su.jrat.client.Antivirus;
import su.jrat.client.Slave;


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