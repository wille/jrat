package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Antivirus;
import pro.jrat.Slave;

public class Packet69Antivirus extends AbstractIncomingPacket {

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
