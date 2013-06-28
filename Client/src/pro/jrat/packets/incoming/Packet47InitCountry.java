package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;
import pro.jrat.settings.Settings;
import pro.jrat.settings.Statistics;
import pro.jrat.ui.frames.Frame;
import pro.jrat.utils.Utils;


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
