package com.redpois0n.packets;

import com.redpois0n.Settings;
import com.redpois0n.Slave;
import com.redpois0n.Statistics;
import com.redpois0n.ui.frames.Frame;
import com.redpois0n.utils.Util;

public class PacketCOUNTRY extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
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
