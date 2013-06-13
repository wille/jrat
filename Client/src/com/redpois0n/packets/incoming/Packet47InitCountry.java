package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.settings.Settings;
import com.redpois0n.settings.Statistics;
import com.redpois0n.ui.frames.Frame;
import com.redpois0n.utils.Util;

public class Packet47InitCountry extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String country = slave.readLine().toLowerCase();
		
		if (!Settings.getGlobal().getBoolean("geoip")) {
			slave.setCountry(country);	
			
			int row = Util.getRow(slave);
			
			if (row != -1) {
				Frame.mainModel.setValueAt(slave.getCountry().toUpperCase().trim(), row, 0);
				Frame.mainTable.repaint();
			}
		}
		
		Statistics.getGlobal().add(slave);
	}

}
