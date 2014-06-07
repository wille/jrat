package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import su.jrat.client.Slave;
import su.jrat.client.settings.Settings;
import su.jrat.client.settings.Statistics;
import su.jrat.client.ui.frames.Frame;
import su.jrat.client.utils.Utils;


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
