package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import su.jrat.client.Slave;
import su.jrat.client.ui.frames.Frame;
import su.jrat.client.utils.Utils;


public class Packet22InitUsername extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String line = slave.readLine();
		slave.setUsername(line);
		Frame.mainModel.setValueAt(slave.getUsername() + "@" + slave.getComputerName(), Utils.getRow(3, slave.getIP()), 5);
	}

}
