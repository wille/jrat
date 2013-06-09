package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.Frame;
import com.redpois0n.utils.Util;

public class PacketUSERNAME extends AbstractPacket {

	@Override
	public void read(Slave slave, String line) throws Exception {
		line = slave.readLine();
		slave.setUsername(line);
		Frame.mainModel.setValueAt(slave.getUsername() + "@" + slave.getComputerName(), Util.getRow(3, slave.getIP()), 5);
	}

}
