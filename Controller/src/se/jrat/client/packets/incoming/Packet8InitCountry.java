package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.client.settings.Settings;

public class Packet8InitCountry extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String country = slave.readLine();

		if (!Settings.getGlobal().getBoolean("geoip")) {
			slave.setCountry(country);	
		}

	}

}