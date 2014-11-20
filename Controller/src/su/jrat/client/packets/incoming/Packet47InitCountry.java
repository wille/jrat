package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import su.jrat.client.Slave;
import su.jrat.client.settings.Settings;

public class Packet47InitCountry extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String country = slave.readLine().toLowerCase();

		if (!Settings.getGlobal().getBoolean("geoip")) {
			slave.setCountry(country);	
		}

	}

}
