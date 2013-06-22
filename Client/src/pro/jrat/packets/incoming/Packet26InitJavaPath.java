package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;


public class Packet26InitJavaPath extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {

		String path = slave.readLine();
		slave.setJavaPath(path);

	}
}
