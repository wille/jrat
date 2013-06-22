package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;


public class Packet23InitInstallPath extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String line = slave.readLine();
		slave.setServerPath(line);

	}

}
