package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.Frame;
import com.redpois0n.util.Util;

public class PacketCOMPUTERNAME extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		slave.setComputerName(slave.readLine());
		Frame.mainModel.setValueAt("Unknown@" + slave.getComputerName(), Util.getRow(3, slave.getIP()), 5);
	}

}