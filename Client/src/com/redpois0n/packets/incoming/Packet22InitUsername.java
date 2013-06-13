package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.Frame;
import com.redpois0n.utils.Util;

public class Packet22InitUsername extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String line = slave.readLine();
		slave.setUsername(line);
		Frame.mainModel.setValueAt(slave.getUsername() + "@" + slave.getComputerName(), Util.getRow(3, slave.getIP()), 5);
	}

}
